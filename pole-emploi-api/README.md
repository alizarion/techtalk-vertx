<p align="center">
<img src="https://i.imgur.com/YXdDFi6.jpg" width="250" alt="pole-emploi">
</p>
<h1 align="center">
Pôle Emploi API
</h1>
<p align="center">
An unofficial client for Pole Emploi API written in Typescript
</p>

<div> 
</div>

## ✨ Features:


## 🔧 Installation

```bash
npm i pole-emploi
```

## 🌐 Usage

Import `pole-emploi` module in your project and initialize it with your [apiKey and apiSecret](https://www.emploi-store-dev.fr/portail-developpeur/catalogueapi).

```js

import PoleEmploi from "pole-emploi";

const PoleEmploiClient = new PoleEmploi({
    apiKey: process.env.API_KEY,
    apiSecret: process.env.API_SECRET
})
PoleEmploiClient.search({theme: 6, 
                         natureContrat: "E2",
                         motsCles: "naval",
                        })


PoleEmploiClient.search({theme: 6, 
                         natureContrat: "E2",
                         motsCles: "naval",
                        }).then((data) => {
    console.log(data)
})

```





