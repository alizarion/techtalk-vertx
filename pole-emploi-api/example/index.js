import PoleEmploi from "../dist/index.umd.js";
import dotenv from "dotenv"

dotenv.config()

const PoleEmploiClient = new PoleEmploi({
    apiKey: process.env.API_KEY,
    apiSecret: process.env.API_SECRET
})

let results = []
let page = 0

function listDesOffres(params) {
    return PoleEmploiClient.search(params)
}

function next(params) {
    const endRange = page + 100
    params.range = `${page}-${endRange}`
    page = endRange
    return listDesOffres(params).then(function (result) {
        results = results.concat(result.resultats)
        let total = 0
        result.filtresPossibles[0].agregation.forEach(aggreg => {
            total += aggreg.nbResultats
        })
        if (results.length < total) {
            // run the operation again
            return next(params);
        } else {
            return results;
        }
    });
}


next({
    theme: 6, //"Métiers de la mer, du nautisme et de la construction navale"
    natureContrat: "E2",
    motsCles: "naval",
}).then(function (results) {
    results = results.filter(result => {
        console.log(result)

        return true//(result.description.search(/naval/i) > 0 && !!result.entreprise && !!result.entreprise.nom)
    })
    console.log(results.length)
    //console.log(results)


})
    .catch(function (err) {
        console.log(err)
    });
