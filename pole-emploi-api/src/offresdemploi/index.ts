import {SearchOffresParams} from "./types";
import {Partenaire} from "../partner";

const resourceName = '/offresdemploi'
const version = '/v2'
const requiredScopes = ['api_offresdemploiv2', 'o2dsoffre']
import qs from 'querystringify'

export class Offres extends Partenaire {

    requiredScopes(): string[] {
        return requiredScopes;
    }

    version(): string {
        return version;
    }

    resourceName(): string {
        return resourceName;
    }

    search(params?: SearchOffresParams) {

        let query = '/offres/search'
        if (params) {
            query += qs.stringify(params, '?')
        }
        return this.request<Offres>(query)
    }

}