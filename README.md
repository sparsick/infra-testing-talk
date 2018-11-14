# infra-testing-talk
You can find here the slides and the sample code of my talk "Testen von und mit Infrastruktur - 'Integration testing done right ;)'" that I presented on Continuous Lifecycle in Mannheim at 14th November 2018.


## Simulate Infrastructure in Software Tests
All code sample for simulating infrastucture in software tests are in `infra-testing-demo-app`.
The code samples are tested with Java 10, embedded in a Spring Boot 2.0.3 skeleton.
Following test libraries are used:
- JUnit 5.3.1 including JUnit 4 (JUnit Jupiter Vintage)
- AssertJ 3.10.0
- MockServer 5.3.0
- Greenmail 1.5.7
- Testcontainers 1.10.0
- REST assured 3.1.0

### Tests against own REST API
The test class `StarWarsMovieControllerTest` shows how to test own REST API with Spring MVC and REST assured.

### Mock REST dependencies
The test class `StarWarsClientTest` shows how to mock dependencies to a REST API with MockServer.

### Testing interaction with E-Mails
The test class `MailClientTest` shows how to test interaction with e-mails (currently only sending) with Greenmail

### Testing interaction with Database
The test class `PersonRepositoryJUnit4/5Test`shows how to test the repository logic including the database that is used in production with Testcontainers.
Test class `DBMigrationJUnit4/5Test` shows how to test the database migration script inside my Maven build.

## Infrastructure as Code Testing
All code samples for infrastructure as code testing are in `infrastructure-as-code-testing`.
The code samples are tested with Bash, Ansible 2.6.1  and Docker 18.06.0-ce.

Following test tools are used:
- Shellcheck 0.4.6
- Ansible-lint 3.4.23
- Serverspec 2.41.3
- Testinfra 1.14.1
- hadolint 1.10.4
- Container Structure Test 1.4.0

### Setup Test Infrastructure
I prepare some Vagrantfiles for the setup of the test infrastructure if necessary. The only prerequires are that you have to install VirtualBox and Vagrant on your machine. It is tested with Vagrant 2.0.0 . Then follow these steps:

1. Open a CLI and go to the location of the file `Vagrantfile`.
2. Call `vagrant up`. Vagrant will download the necessary image for VirtualBox. That will take some times.
3. Then copy your public key for the authentication that is needed for a SSH login. A setup SSH script exists for this: `./setupSSH.sh`

Hint: Public and private keys can be generated with the following command: `ssh-keygen`


### Shell Scripts
Shell script sample is in `infrastructure-as-code-testing/shell`.
This is also the location for the next CLI calls.

- Code quality check for the shell script:  `shellcheck setup-svn.sh`.

### Ansible Playbooks
Ansible playbook samples are in `infrastrucutre-as-code-testing/ansible`.
This is also the location for the next CLI calls.

- Code quality check for Ansible playbooks: `ansible-lint *.yml`
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
