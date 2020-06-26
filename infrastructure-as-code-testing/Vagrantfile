# -*- mode: ruby -*-
# vi: set ft=ruby :

# All Vagrant configuration is done below. The "2" in Vagrant.configure
# configures the configuration version (we support older styles for
# backwards compatibility). Please don't change it unless you know what
# you're doing.
Vagrant.configure(2) do |config|
  # The most common configuration options are documented and commented below.
  # For a complete reference, please see the online documentation at
  # https://docs.vagrantup.com.

  config.trigger.after :up do |trigger|
    trigger.name = "Copy SSH key"
    trigger.info = "Copy SSH key"
    trigger.run = {path: "setupSSH.sh"}
  end

  config.vm.define "test" do |vbox|
    vbox.vm.hostname = "test"

    # Every Vagrant development environment requires a box. You can search for
    # boxes at https://atlas.hashicorp.com/search.
    vbox.vm.box = "generic/ubuntu2004"
    # Create a private network, which allows host-only access to the machine
    # using a specific IP.
    vbox.vm.network "private_network", ip: "192.168.33.10"
    vbox.vm.provision "shell", inline: <<-SHELL
#        sudo apt-get update
    #   sudo apt-get install -y apache2
    SHELL
  end

  config.vm.provider "virtualbox" do |vb|
    vb.customize ["modifyvm", :id, "--memory", "2048"]
  # vb.customize ["modifyvm", :id, "--natdnshostresolver1", "on"]
  # vb.customize ["modifyvm", :id, "--natdnsproxy1", "on"]
  end
end
