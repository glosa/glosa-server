<p align="center">
  <img src="logo.png" alt="logo" width="200"> 
</p>
<h1 align="center">Glosa: Comments for static sites.</h1>
<p align="center">
   <strong >Clone of Disqus, but faster, Opensource and sexy.</strong>
</p>

## Usage

Download Glosa.

``` sh
curl dist/glosa.jar -o glosa.jar
```

Run.

```sh
java -jar glosa.jar
```

🎉 Great 🎉. You already have your 🔥 own comment server🔥.

## Install

``` sh
cp config.yaml.example config.yaml
```

Edit `config.yaml`.

``` yaml
domain: localhost
port: 4000
debug: false
```

Run.

``` sh
java -jar glosa.jar
```

That's it, now you just have to test that it works properly.

``` sh
curl localhost:4000/api/v1/captcha/
```

It will return a random token

``` json
{"token":"OABWNONEOOKXRMMWADPF"}
```

## Testing

``` sh
lein check-idiomatic
lein check-format
```

## API

### Get Comments

Get from `https://glosa.example/best-SO/`.

``` sh
;; GET
/api/v1/comments/?url=https://glosa.example/best-SO/
```

### Add Comment

### Get captcha token
