JHipster Test combinations
==========================

Naming conventions of test project

Prefix jh-
Don't add anything to the name for default selection.



Project Choices
===============
* Authentication
	- HTTP Session Authentication (stateful, default Spring Security mechanism) => Default
	- HTTP Session Authentication with social login enabled (Google, Facebook, Twitter). Warning, this doesn't work with Cassandra! => ASoc
	- OAuth2 Authentication (stateless, with an OAuth2 server implementation) => OAut
	- Token-based authentication (stateless, with a token) => ATok
* Database
	- SQL (H2, MySQL, PostgreSQL, Oracle) => Default
	- MongoDB => DbM
	- Cassandra => DbC
* Production Database
	- SQL (H2, MySQL, PostgreSQL, Oracle) => Default
	- MongoDB => DbPM
	- Cassandra => DbPC
* Development Database
	- H2 with disk-based persistence => Default
	- H2 with in-memory persistence => DbDH
	- MySQL => DbDS
* Hibernate 2nd level cache
	- No => No2Cache
	- Yes, with ehcache (local cache, for a single node) => Default
	- Yes, with HazelCast (distributed cache, for multiple nodes) => Hazelcast
* Search engine in your application?
	- No => Default
	- Yes, with ElasticSearch => Search
* Clustered HTTP sessions?
	- No => Default
	- Yes, with HazelCast => HtHz
* Maven or Gradle for building the backend?
	- Maven => Default
	- Gradle => Gradle
* Grunt or Gulp
	- Grunt => Default
	- Gulp => Gulp
* Sass
	- n => Default
	- y => Sass
* Angular Translate
	- y => Default
	- n => NoTranslate
* Testing Framework(s)
	- Gatling => Default or TestG if others are selected
	- Cucumber => TestC
 	- Protractor => TestP
- ws: Use web sockets, default false.



Entity choices
==============
* Use Data Transfer Object:
	[NoDto (default) | Dto]
* Separate Service Class:
	- No: (default)
	- Yes, generate a separate service class => Service
	- Yes, generate a separate service interface and implementation => ServiceIf
* Pagination
 	- No (default)
 	- Yes, with a simple pager => Pager
 	- Yes, with pagination links => Pagination
 	- Yes, with infinite scroll => Infinite

