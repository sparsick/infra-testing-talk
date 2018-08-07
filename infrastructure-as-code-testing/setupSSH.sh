#!/bin/bash
ssh-keygen -R 192.168.33.10
ssh-keyscan -t rsa -H 192.168.33.10 >> ~/.ssh/known_hosts
sshpass -p 'vagrant' ssh-copy-id -i ~/.ssh/id_rsa.pub vagrant@192.168.33.10
