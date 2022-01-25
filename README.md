# infra-testing-talk

![Build Status](https://github.com/sparsick/infra-testing-talk/workflows/MavenBuild/badge.svg)

You can find here the slides and the sample code of my talk "Testen von und mit Infrastruktur".


## Simulate Infrastructure in Software Tests
All code sample for simulating infrastucture in software tests are in `infra-testing-demo-app`.
The code samples are tested with Java 17 and Groovy 3.0.9 embedded in a Spring Boot 2.6.3 skeleton.
Following test libraries are used:
- JUnit 5.8.2 including JUnit 4 (JUnit Jupiter Vintage)
- AssertJ 3.21.0
- MockServer 5.11.2
- Wiremock 2.27.2
- Greenmail 1.6.5
- Testcontainers 1.14.3
- REST assured 4.3.1
- Spock 2.0

### Tests against own REST API
The test class `StarWarsMovieControllerITest` shows how to test own REST API with Spring MVC and REST Assured.
Test classes `RestAssuredJsonPathTest` and `RestAssuredXmlPathTest` demonstrate REST Assured JsonPath and XmlPath feature.

### Mock REST dependencies
The test classes `StarWarsClientMockserverTest`, `StarWarsClientWiremockTest` and `StarWarsClientMockserverGroovyTest` show how to mock dependencies to a REST API with MockServer or Wiremock.
Test class `StarWarsClientVerifiedFakeTest` shows a sample how to implement a verify fake test.

### Testing interaction with E-Mails
The test class `MailClientTest` shows how to test interaction with e-mails with Greenmail

### Testing interaction with Database
The test class `PersonRepositoryJUnit4/5/SpockTest`shows how to test the repository logic including the database that is used in production with Testcontainers.
Test class `DBMigrationJUnit4/5Test` shows how to test the database migration script inside my Maven build.
Test class `PersonRepositoryJdbcUrlTestContainerTest`shows Testcontainers JDBCUrl feature.
Test classes `*SinglwtonContainerTests` show how to implement the singlton container pattern.

## Infrastructure as Code Testing
All code samples for infrastructure as code testing are in `infrastructure-as-code-testing`.
The code samples are tested with Bash, Ansible 2.9.6  and Docker 19.03.12.

Following test tools are used:
- Shellcheck 0.7.0
- Ansible-lint 4.2.0
- yamllint 1.23.0
- Serverspec 2.41.5
- Testinfra 5.2.1
- hadolint 1.18.0
- Container Structure Test 1.9.0
- Molecule 3.0.4

### Setup Test Infrastructure
I prepare some Vagrantfiles for the setup of the test infrastructure if necessary.
The only prerequires are that you have to install VirtualBox and Vagrant on your machine.
It is tested with Vagrant 2.2.9 .
Then follow these steps:

1. Open a CLI and go to the location of the file `Vagrantfile`.
2. Call `vagrant up`. Vagrant will download the necessary image for VirtualBox. That will take some times.

Hint: Public and private keys can be generated with the following command: `ssh-keygen`


### Shell Scripts
Shell script sample is in `infrastructure-as-code-testing/shell`.
This is also the location for the next CLI calls.

- Code quality check for the shell script:  `shellcheck setup-svn.sh`.

### Ansible Playbooks
Ansible playbook samples are in `infrastrucutre-as-code-testing/ansible`.
This is also the location for the next CLI calls.

- Code quality check for Ansible playbooks with Ansible-lint: `ansible-lint *.yml`
- Code quality check for YAML file in common with yamllint: `ỳamllint *yml`
- Running ansible playbooks against the test infrasructure (see chapter 'Setup Test Infrastructure' above):
```
ansible-playbook -i inventories/test -u vagrant setup-db.yml # MySQL setup
ansible-playbook -i inventories/test -u vagrant setup-app.yml # Apache Tomcat setup
```
- Running serverspec tests against a provisioned VM: `rake spec`
- Running testinfra tests against a provisioned VM:
```
py.test --connection=ansible --ansible-inventory inventories/test -v tests/*.py
```
#### Molecule
Molecule is an aggregator over some testing tools for Ansible roles.

For running Molecule go to `infrasructure-as-code-testing/ansible/roles/tomcat` and run `molecule test`

### Docker Image
Dockerfile sample is in `infrastrucutre-as-code-testing/docker`.
This is also the location for the next CLI calls.

- Code quality check for Ansible playbooks: `hadolint tomcat.df`
- Build the Docker image based on `tomcat.df`:
```
docker build -t sparsick/tomcat9 -f tomcat.df .
```
- Running the Container Structure Tests (prerequisite Docker image is built before):
```
container-structure-test test --image sparsick/tomcat9:latest --config tomcat-test.yaml
```
