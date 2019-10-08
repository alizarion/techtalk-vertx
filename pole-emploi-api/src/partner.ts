import {Base} from "./base";
import fetch from "isomorphic-unfetch";

const rootPath = '/partenaire'

type Auth = {
    access_token: string,
    scope: string,
    token_type: string,
    expires_in: number
}

export abstract class Partenaire extends Base {


    abstract requiredScopes(): string[]

    abstract version(): string

    abstract resourceName(): string

    protected authenticate<Auth>(): Promise<Auth> {
        const authUrl = `https://entreprise.pole-emploi.fr/connexion/oauth2/access_token?realm=${rootPath}`
        const data = new URLSearchParams()
        const scopes = `${this.requiredScopes().join(" ")} application_${this.apiKey}`

        data.set('grant_type', 'client_credentials')
        data.set('client_id', this.apiKey)
        data.set('client_secret', this.apiSecret)
        data.set('scope', scopes)

        const options = {
            method: 'POST',
            body: data,
        }
        const headers = {
            'Content-type': 'application/x-www-form-urlencoded; charset=utf-8'
        }

        const config = {
            ...options,
            headers,
        }
        return fetch(authUrl, config).then(r => {
            if (r.ok) {
                return r.json()
            }
            throw new Error(r.statusText)
        })
    }

    protected request<T>(query?: string, options?: RequestInit): Promise<T> {
        const url = this.basePath + rootPath + this.resourceName() + this.version() + query
        return this.authenticate().then((auth: Auth) => {
            if (auth.access_token) {
                const headers = {
                    'Authorization': `Bearer ${auth.access_token}`,
                    'Content-type': 'application/json',
                }
                const config = {
                    ...options,
                    headers,
                }
                return fetch(url, config).then(r => {
                    if (r.ok) {
                        return r.json()
                    }
                    throw new Error(r.statusText)
                })

            } else {
                throw new Error("error")
            }
        })

    }
}
