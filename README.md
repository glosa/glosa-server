# Glosa

<p align="center">
  <img src="logo.png" alt="logo" width="200"> 
</p>
<h1 align="center">Comments for static sites.</h1>
<p align="center">
   <strong >Clone of Disqus, but faster, Opensource and sexy.</strong>
</p>

## Usage

```sh
lein run
```

## Install

```sh
cp config.yaml.example config.yaml
```

## Testing

``` sh
lein check-idiomatic
lein check-format
```

## API

### Comments

Get from `https://glosa.example/best-SO/`.

``` sh
;; GET
/api/v1/comments/?url=https://glosa.example/best-SO/
```
