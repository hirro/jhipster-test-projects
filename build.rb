
class String
  def underscore
    self.gsub(/::/, '/').
    gsub(/([A-Z]+)([A-Z][a-z])/,'\1_\2').
    gsub(/([a-z\d])([A-Z])/,'\1_\2').
    tr("-", "_").
    downcase
  end
end

projectDirectories =  ['jh', 'jh-Search']
entities = [ 'TestDefaultObject', 'TestDtoPagination', 'TestDtoService', 'TestInfinite', 'TestPage', 'TestPagination']

# Remove all uncommited files
system("git clean -d -x -f")

# Rebuild test projects
projectDirectories.each { |project| 
	Dir.chdir( project ) do 

		# Generate project
		system "yo jhipster --force"

		# Generate entities
		system "cp -r ../entities .jhipster"
		entities.each { |entity| 
			system("yo jhipster:entity #{entity} --force")
		}

		# Add mock entity data
		system("cp ../MOCK_DATA.csv src/main/resources/config/liquibase/testdata.csv")

		# Create db file
		changelog = "#{project}/src/main/resources/config/liquibase/changelog/testdata.xml"
		File.open("../#{project}/src/main/resources/config/liquibase/changelog/testdata.xml", 'w+') { |f|
			f.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n")
			f.write("<databaseChangeLog\n")
			f.write("    xmlns=\"http://www.liquibase.org/xml/ns/dbchangelog\"\n")
			f.write("    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n")
			f.write("    xsi:schemaLocation=\"http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.4.xsd\">\n")
			f.write("    <changeSet id=\"20160123160820\" author=\"jhipster\">\n")
			entities.each { |entity| 
				entityTableName = "#{entity.underscore}"
				f.write("        <loadData encoding=\"UTF-8\" file=\"config/liquibase/testdata.csv\" separator=\";\" tableName=\"#{entityTableName}\"/>\n")
			}
			f.write("    </changeSet>\n")
			f.write("</databaseChangeLog>\n")
		}
		puts "Add the file #{changelog} to `src/main/resources/config/liquibase/master.xml`"
	end
}