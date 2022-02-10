# complaints-service
Produces buyer complaints

**Prerequisites:**
Java
Maven
PostgreSQL (Locally tested on v 14)
Kafka

**Setting a local environment**
Projects:
https://github.com/roybenenosh/complaints-service
https://github.com/roybenenosh/complaints-consumer

DB:
Open SQL Shell (psql) and connect to the db
All the required details are default except: user: postgres; password: admin; database: intuit.
If you are using IntelliJ IDEA, it is recommended connecting to the data source for more comfortable interface.

After connecting to db, create a complaint table with the command:
CREATE TABLE complaints(id UUID NOT NULL DEFAULT uuid_generate_v1(), user_id VARCHAR(64), subject VARCHAR(255), complaint VARCHAR(255), purchase_id VARCHAR(64), creation_date DATE NOT NULL, PRIMARY KEY (id));

Kafka
Navigate to your local kafka directory and run the commands:
bin/zookeeper-server-start.sh config/zookeeper.properties
bin/kafka-server-start.sh config/server.properties

Services
Run the complaint-service and complaint-consumer services
