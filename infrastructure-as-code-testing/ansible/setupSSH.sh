#!/bin/bash
ssh-keygen -R 192.168.33.10
ssh-copy-id -i ~/.ssh/id_rsa.pub vagrant@192.168.33.10
