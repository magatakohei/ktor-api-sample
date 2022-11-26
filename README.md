# Ktor Server try Project

Ktor Serverの技術検証をするレポジトリ

## Tech Stack

|Tech|ver|
|---|---|
|Kotlin |1.7.20|
|Ktor| 2.1.3|

## Installation

**Install my-project**

```bash
git clone git@github.com:magatakohei/ktor-http-sample-.git
cd ktor-http-sample-
```

**Create JWT RS256 private key**

```bash
openssl genrsa -out private.key 2048
openssl rsa -in private.key -pubout -out public.key
cat private.key.pk8 | tr -d "\n" | sed -e "s/-----BEGIN PRIVATE KEY-----//" -e "s/-----END PRIVATE KEY-----//"
```

## Deployment

To deploy this project run

```bash
# Auro Realod
./gradlew -t build -x test -i

# project run
./gradlew run 
```

## Authors

- [@magatakohei](https://www.github.com/magatakohei)

## Documentation

[Ktor Documentation](https://ktor.io/docs/welcome.html)

