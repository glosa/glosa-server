<p align="center">
  <img src="logo.png" alt="logo" width="200"> 
</p>
<h1 align="center">Glosa: Comments for static sites.</h1>
<p align="center">
   <strong >Clone of Disqus, but faster, Opensource and sexy.</strong>
</p>

## Amazing reasons to use it

- Only 2ms to respond.
- No database.
- Configuration in a simple YAML.
- Easy to import from Disqus.
- Captcha system included.
- Easy to integrate with static pages.
- Opensource.

## Usage

Download Glosa.

``` sh
curl dist/glosa.jar -o glosa.jar
```

Run.

```sh
java -jar glosa.jar
```

Great 🎉. You already have your 🔥 own comment server 🔥.

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

Gets all the comments on one page.

| Param | Value  |
|---|---|
| url  | string |

#### Example

Get from `https://glosa.example/best-SO/`.

``` sh

/api/v1/comments/?url=https://glosa.example/best-SO/
```

#### Response

``` json
[
    {
        "id": 4812781236,
        "parent": "",
        "createdAt": 1584266634,
        "thread": "https://glosa.example/best-SO/",
        "author": "Lexar",
        "message": "Do you use Glosa too? It's an amazing technology."
    },
    {
        "id": 4812781237,
        "parent": "",
        "createdAt": 1584266746,
        "thread": "https://glosa.example/best-SO/",
        "author": "Lucia",
        "message": "I love the article."
    }
]
```

### Add Comment

### Get captcha token

---

## Import from Disqus
https://github.com/tanrax/glosa-disqus-import

---

## Connect with your site (HTML and Javascript)
https://github.com/tanrax/glosa-cli

---

<p align="center">
  Thanks to the power of <a href="https://tadam-framework.dev/"><img src="https://avatars3.githubusercontent.com/u/54397807?s=50&v=4" alt="logo" width="50"> Tadam Framework</a>
</p>
