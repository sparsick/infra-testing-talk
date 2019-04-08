import os

import testinfra.utils.ansible_runner

testinfra_hosts = testinfra.utils.ansible_runner.AnsibleRunner(
    os.environ['MOLECULE_INVENTORY_FILE']).get_hosts('all')


def test_openjdk_is_installed(host):
    openjdk = host.package("openjdk-8-jdk")
    assert openjdk.is_installed


def test_tomcat_service_exists(host):
    assert host.file("/etc/systemd/system/tomcat.service").exists


def test_tomcat_folder_exists(host):
    assert host.file("/opt/tomcat").exists
