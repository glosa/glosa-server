<p align="center">
  <img src="media/logo.png" alt="logo" width="200"> 
</p>
<h1 align="center">Glosa: Comments for static sites.</h1>
<p align="center">
   <strong >Clone of Disqus, but faster, Opensource and sexy.</strong>
</p>

## Amazing reasons to use it

- **Very fast**, responses between 5ms and 15ms on average.
- Easy to integrate with **static pages**.
- **Easy to import** from Disqus.
- **No database**, everything is stored in a JSON.
- Configuration in a **simple YAML**.
- **Captcha** system included.
- **Sends you an email** when you receive a new message.
- **Multisite**: Single server for multiple websites.
- **Opensource**.

## How does it work?

<p align="center">
  <img src="media/explication.png" alt="logo" width="90%"> 
</p>

On the one hand we have Glosa who would be our comment server. It **feeds in GET and POST requests**, and obediently **returns JSON**. It can only return **comments from a url** (it sorts comments by url, not ids) or **create a new comment** (**parent or child** of another comment). Nothing else. If you want to create a comment previously you will need to ask for the token to confirm that you are not a robot.

**Optionally you can receive an email** automatically when a new comment is written.

The website, CMS or mobile application, must integrate a **logic with Javascript** to make the necessary requests and render the comments properly. To make this task easier we have created an example template that you can modify to your needs. You can find the link on this page.

## Scenarios

### 1 web page, share or not a server

<p align="center">
  <img src="media/scenary-1.png" alt="logo" width="500px"> 
</p>

### 3 web pages in different domains or servers

<p align="center">
  <img src="media/scenary-2.png" alt="logo" width="500px"> 
</p>


## Origin

A Glosa is a **Spanish word**. It is defined as a **note**, usually brief, that is **written in the margin of a text** or even between its lines with the intention of clarifying some idea of it. 

The software was born with the **intention that the author's static blog would no longer depend on an external company** (Disqus), and could have control of its content. To make it as easy as possible to deploy, develop and maintain; he programmed in **Clojure**. And from the beginning it was clear to him that he didn't need a conventional database, **plain text was enough**.

## Run

1) Make sure you have Java installed.

2) Create a file `config.yaml` with the following content. You can also use `config.yaml.example` as a base config and change it to fit your needs.

``` yaml
##### General #####
# If it is active it will be accessible to any client
debug: false
# It can be a domain in case of using a proxy: example.com
domain: localhost
port: 4000
# It indicates which domain can use it. Debug true so there are no limitations.
domain-cli: "http://example-cli.com/"

##### Notify #####
# Type of notification, currently valid: email
notify: email
subject: New comment
from: server@example.com
to: user@example.com
# SMTP, only notify: email
smtp-host: smtp.example.com
smtp-user: smtpuser
smtp-password: smtppassword
smtp-port: 25
smtp-tls: true

##### Captcha #####
# Currently valid: time
captcha: time

##### Database #####
# Currently valid: plain
database: plain
```

3) Download the latest version of Glosa (`glosa-{version}-standalone.jar`).

https://github.com/glosa/glosa-server/releases


4) Now you can execute glosa.

```sh
java -jar target/glosa-{version}-standalone.jar
```

Great 🎉. You already have your 🔥 own comment server 🔥.

That's it, now you just have to test that it works properly.

``` sh
curl localhost:4000/api/v1/captcha/
```

It will return a random token

``` json
{"token":"OABWNONEOOKXRMMWADPF"}
```

## Create your own JAR

1) Make sure you have **openjdk** or **oracle-jdk** installed, **clojure** and  **leiningen**.

### MacOS

``` sh
brew install openjdk clojure leiningen
```

### Debian/Ubuntu

``` sh
sudo apt install default-jdk clojure leiningen
```

2) Clone the repository and enter the generated folder.

``` sh
git clone https://github.com/glosa/glosa-server.git
cd glosa-server
```

3) Run the following command to build a `jar` file.

`lein uberjar`

After this two files should be created in `target/`. We will use the standalone version: `glosa-{version}-standalone.jar`.

---

## Example connect with your static site (only HTML and Javascript)
https://github.com/glosa/glosa-static-integration

---

## Import your comments from Disqus
https://github.com/glosa/glosa-disqus-import

---

## API

### Get Comments

GET: Gets all the comments on one page.

``` sh
/api/v1/comments/?url={url}
```

| Param | Value  | Description |
|---|---|---|
| url  | string | Page where you want to get the comments. |

#### Example

Get from `https://glosa.example/best-SO/`.

``` sh
curl 'https://programadorwebvalencia.localhost:4000/api/v1/comments/?url=https://glosa.example/best-SO/'
```

#### Success response

``` json
[
    {
        "id": 4812781236,
        "parent": "",
        "deep": 0,
        "createdAt": 1584266634,
        "thread": "https://glosa.example/best-SO/",
        "author": "Lexar",
        "message": "Do you use Glosa too? It's an amazing technology."
    },
    {
        "id": 4812781237,
        "parent": "4812781236",
        "deep": 1,
        "createdAt": 1584266746,
        "thread": "https://glosa.example/best-SO/",
        "author": "Lucia",
        "message": "I love the article."
    }
]
```

#### Fail response

``` json
[]
```

### Add Comment

POST: Add new comment on one page. After saving the comment the token will no longer be valid.

``` sh
/api/v1/comments/
```

| Param | Value | Description |
|---|---|---|
| parent  | number | If it's a sub-comment, the number of the parent comment. Otherwise leave empty. |
| author  | string | Author's name. |
| message  | string | Message. It can be HTML or plain. |
| token  | number | Number of the token generated by the captcha endpoint. |
| thread  | string | Page where you want to save the comment. |

#### Example

Save comment from `https://glosa.example/best-SO/`.

``` sh
curl -H "Content-type: application/json" -d '{
	"parent": "",
	"token": "VRJUOBBMTKFQUAFZOKJG",
	"author": "Juana",
	"message": "I like it very much.",
	"thread":"https://glosa.example/best-SO/"
}' 'https://glosa.example:4000/api/v1/comments/'
```

#### Success response

``` json
{
    "status": 200
}
```

#### Fail response


``` json
{
    "status": 401
}
```

### Get captcha token

GET: Get a token to validate that a new comment can be created. It has only one use. It must also be obtained 20 seconds before use or it will not work.

``` sh
/api/v1/captcha/?url={url}
```

| Param | Value  | Description |
|---|---|---|
| url  | string | Page where you want to save the comment. |

#### Example

Get token for page `https://glosa.example/best-SO/`.

``` sh
curl 'https://glosa.example:4000/api/v1/captcha/?url=https://glosa.example/best-SO/'
```

#### Success response

``` json
{
    "token": "ZRFOKXLALKNPOJPYJLVY"
}
```

#### Fail response


``` json
{
    "token": ""
}
```
---

## Manager script

To **manage some minor features** you can use the `manager` script which will filter, modify or delete the database. Previously **remember to stop Glosa** to avoid problems.

You will need to have **Node installed** on your computer and give it **permission to run**.

### Take all comments by thread

``` sh
./manager get [thread]
```

Example

``` sh
./manager get https://glosa.example/best-SO/
```

### Update the text of a comment

``` sh
./manager update [id] [new message]
```

Example

``` sh
./manager update 1234 'I love your article.'
```

### Delete a comment

``` sh
./manager delete [id]
```

Example

``` sh
./manager delete 1234
```

---

<p align="center">
  Thanks to the power of <a href="https://tadam-framework.dev/"><img src="https://avatars3.githubusercontent.com/u/54397807?s=50&v=4" alt="logo" width="50"> Tadam Framework</a>
</p>
