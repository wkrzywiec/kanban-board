#  Kanban Dashboard Application

## Install Application in K8S platform.

```sh
cd Charts
helm install kanbanapp -n namespace ./
```

## Accessing the UI component:

* The UI component is accessed via Load balancer.
* If the envionment already has a Ingress solution then we can use it as well.

## Access the kanban Backend:

* Backend is only accessible within the cluster as it is running on cluster IP configuration.

## Docker Image Build

* The CI is performed using Github actions.
* The images are built and pushed as part of GitHub Actions.


## Kanban App Helm Variables

| Name | Description | Type | Required |
|------|-------------|:----:|:-----:|
| image.repository | Name of the image to deploy | string | yes |
| image.tag | Image Tag to deploy | string |  yes |
| image.pullPolicy | Image pullPolicy | string | yes |
| replicas | Number of repica to deploy | string | yes |
| fullname | Name of the application | string | yes |
| serviceType | Application service type | string | yes |

## Kanban UI Helm Variables

| Name | Description | Type | Required |
|------|-------------|:----:|:-----:|
| image.repository | Name of the image to deploy | string | yes |
| image.tag | Image Tag to deploy | string |  yes |
| image.pullPolicy | Image pullPolicy | string | yes |
| replicas | Number of repica to deploy | string | yes |
| fullname | Name of the application | string | yes |
| serviceType | Application service type | string | yes |


## Helm secrets Variables

| Name | Description | Type | Required |
|------|-------------|:----:|:-----:|
| fullname | Name of the config | string | yes |
| POSTGRES_DB | Postgres DB Name | string |  yes |
| POSTGRES_USER | Postgres DB Username | string | yes |
| POSTGRES_PASSWORD | Postgres DB Password | string | yes |
| POSTGRES_INITDB_ARGS | Postgres DB Init Arguments| list | string |
