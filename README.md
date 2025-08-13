# Dockerized Web Application

A Python web application deployed using Docker, Ansible, and Jenkins CI/CD pipeline.

## Overview

This project demonstrates DevOps automation by containerising a Python web app and deploying it to remote servers using modern tools.

## Quick Start

1. **Clone the repository**:
   ```bash
   git clone https://github.com/mohamedkamalsabaa/Dockerized_Web_Application.git
   cd Dockerized_Web_Application
   ```

2. **Build and run with Docker**:
   ```bash
   docker build -t mohamedk15/final-project:latest .
   docker run -p 8080:8080 mohamedk15/final-project:latest
   ```

3. **Access the application**: Visit `http://localhost:8080`

## Features

- **Docker containerization** for consistent deployment
- **Ansible automation** for remote server deployment
- **Jenkins CI/CD pipeline** for automated builds and deployments
- **Infrastructure as Code** approach

## Deployment

### Local Development
```bash
docker-compose up -d
```

### Remote Deployment
```bash
cd ansible
ansible-playbook -i inventory/hosts playbooks/deploy.yml
```

## Technologies

- **Docker** - Containerization
- **Python** - Web application
- **Ansible** - Configuration management
- **Jenkins** - CI/CD pipeline
