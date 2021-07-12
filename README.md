# kanban-board-k8s-demo

## Overview

This sample project is forked from https://github.com/wkrzywiec/kanban-board

The repo represents a working example of a Web-App that uses a backend API service (`kanban-app`) with a PostgreSQL database,
and a separate AngularJS frontend (`kanban-ui`).
Everything is containerised with Docker and a sample docker-compose.yml file is available to test it locally.

## Exercise

Your task is to take this sample Web-App and port it to Kubernetes.  The components of the app should, as much as possible, be separately configurable and scalable.
The end result should be usable for deployments to different kubernetes namespaces and/or clusters.
Optionally, the PostgreSQL database should be able to be replaced with a cloud Managed SQL instance
(e.g. [DigitalOcean](https://docs.digitalocean.com/products/databases/), [Google Cloud Platform](https://cloud.google.com/sql), or another cloud provider), but
by default should support a PostgreSQL instance running in the kubernetes cluster.

### Things to consider in your solution

 * Fork this repo on GitHub and commit all your work to your fork.  When completed send the link to your fork of the repo with john.kirkham@ratehub.ca.
 * The Dockerfiles exist already for the `kanban-app` and `kanban-ui` but you will need to build and push the Docker images to a public Docker registry for
 them to be accessable from within a kubernetes cluster.  ([DockerHub](https://hub.docker.com/) or [GCR](https://cloud.google.com/container-registry/)
 are possible solutions for this.)
 * To manage making this installation reconfigurable and relocatable it is strongly recommended that you use a templating solution like [Helm](https://helm.sh/),
 an overlay system like [Kustomize](https://kustomize.io/), or a combination of the two.  Similar alternative are acceptable.
 * Try to follow best practices, especially with regards to basic security in your kubernetes deployment.
 * Tools like [MiniKube](https://minikube.sigs.k8s.io/docs/start/), [KinD](https://kind.sigs.k8s.io/docs/user/quick-start/), [microk8s](https://microk8s.io/),
 or similar will be useful in developing and testing your solution.  Alternatively, free trials are offered by cloud providers like DigitalOcean,
 GCP, Azure, AWS, etc. and may be used for this.
 * If you encounter any problems with any part of the task or are blocked by something, please add a `KNOWN-ISSUES.md` file to your repo and document it there.

### Bonus/Optional Tasks

 * Demonstrate how to expose this externally (viewable outside the kubernetes cluster).
 * Demonstrate some form of secrets-management for security sensitive configurations such as the database credentials or connection string.
 * Improve front-end security by upgrading the base container used for the `kanban-ui` Docker image.
 * Can you optimize the size of the backend `kanban-app` Docker image?
 * Use GitHub Actions (workflows) to automate parts of the build and deployment process.
