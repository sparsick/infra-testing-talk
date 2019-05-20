# infra-testing-talk
You can find here the slides and the sample code of my talk "Testen von und mit Infrastruktur - 'Integration testing done right ;)'" that I presented on Magdeburger Developer Days at 21st May 2019.


## Simulate Infrastructure in Software Tests
All code sample for simulating infrastucture in software tests are in `infra-testing-demo-app`.
The code samples are tested with Java 11, embedded in a Spring Boot 2.1.4 skeleton.
Following test libraries are used:
- JUnit 5.4.1 including JUnit 4 (JUnit Jupiter Vintage)
- AssertJ 3.12.2
- MockServer 5.4.1
- Greenmail 1.5.10
- Testcontainers 1.11.1
- REST assured 3.3.0

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
The code samples are tested with Bash, Ansible 2.7.10  and Docker 18.09.4.

Following test tools are used:
- Shellcheck 0.4.6
- Ansible-lint 4.1.0
- yamllint 1.10.0
- Serverspec 2.41.3
- Testinfra 2.1.0
- hadolint 1.16.3
- Container Structure Test 1.8.0

### Setup Test Infrastructure
I prepare some Vagrantfiles for the setup of the test infrastructure if necessary. The only prerequires are that you have to install VirtualBox and Vagrant on your machine. It is tested with Vagrant 2.2.4 . Then follow these steps:

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
