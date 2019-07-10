# microservice-kotlin-boilerplate

[![Code Style](https://img.shields.io/badge/code%20style-google-green.svg?style=flat-square)](https://google.github.io/styleguide/javaguide.html)
[![MIT](https://img.shields.io/dub/l/vibe-d.svg)](./LICENSE)

## Requirements

* Gradle
* Kotlin
* Docker

## Settings variables

| Name | Description |
| --- |--- |
| `HTTP_HOST` | default `0.0.0.0` |
| `HTTP_PORT` | default `8080` |

## Build

```
./gradlew clean build
```

## Test

#### Unit Tests

```
./gradlew test
```

## Run

1. Build the docker image  
    ```
    docker build . -t microservice-kotlin-boilerplate
    ```
1. Run the image  
    ```
    docker run -p 8080:8080 microservice-kotlin-boilerplate
    ```

## Coding Style

Use [Google Style](https://google.github.io/styleguide/javaguide.html) for the coding style guide.  
Use [ktlint](https://github.com/shyiko/ktlint) to check style.
 
#### Install

```
brew install shyiko/ktlint/ktlint
```

Apply ktlint to IDEA built-in formatter (optional): `ktlint --apply-to-idea`

## Create Release
To create a release which will be deployed to production follow the following steps:

- on **master** branch, create tag
  ```
  git tag -a fix-v1.0.3 -m 'fix: issue#100'
  git push origin refs/tags/fix-v1.0.3
  ```

- use [git-chglog](https://github.com/git-chglog/git-chglog) to generate the changelog
  ```
  git-chglog 1.0.0..2.0.0
  ```

  copy this content to [CHANGELOG.md](CHANGELOG.md) 

- create new release in GitHub and copy generated changelog for the tag to notes

## API

* __url__: `/api/example/{id}`
* __method__: `GET`
* __description__: 
* __query parameters__:
  * {__id__}: `int`, id
* __return on success__: example
  ```json
  {
      "example": 3
  }
  ```
* __return on error__: (i.e. 400 error)
  ```json
  {
    "error": "id parameter not provided"
  }
  ```
* __return on error__: internal server error (i.e. 500 error)
  ```json
  {
    "error": "server error"
  }
  ```
  

License
=======
Licensed under [MIT](./LICENSE).