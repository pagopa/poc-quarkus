# poc-quarkus

Example of a Quarkus microservice

The microservice replicates the functionalities of the project [pagopa-reporting-orgs-enrollment](https://github.com/pagopa/pagopa-reporting-orgs-enrollment).

- [Api documentation](#api-documentation-📖)
- [Technology stack](#technology-stack-💻)
- [Start project locally](#start-project-locally-🚀)
- [Develop project locally](#develop-project-locally-💻)
- [Contributors](#contributors-👥)

---

## Api documentation 📖

See the [Openapi here](https://github.com/pagopa/poc-quarkus/openapi/openapi.json)

---

## Technology stack 💻
- Java 11
- Quarkus
- Hibernate
- JPA

---

## Start native project locally 🚀

### Prerequites
- Java 11
- GraalVM 
### Run
- Launch `generate_native_image.sh` from the project folder
- Execute `docker-compose up` to boot the native application and the database
---

## Develop project locally 💻

### Prerequites
Install and run azurite:
- Install: `docker pull mcr.microsoft.com/azure-storage/azurite`
- Run: `docker run -p 10000:10000 -p 10001:10001 -p 10002:10002 mcr.microsoft.com/azure-storage/azurite`
### Run
`mvn quarkus:dev`
### Testing 🧪
Select `r` after launching the application in developer mode with the previous command. 


## Contributors 👥

Made with ❤️ by PagoPa S.p.A

### Mainteiners

See `CODEOWNERS` file


