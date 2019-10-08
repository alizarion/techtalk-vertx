
type Config = {
    apiKey: string,
    apiSecret: string,
    basePath?: string,
}

export type Pagination = {
    page?: number,
    per_page?: number,
}


export abstract class Base {

    protected apiKey: string
    protected basePath: string
    protected apiSecret: string


    constructor(config: Config) {
        this.apiKey = config.apiKey
        this.apiSecret = config.apiSecret

        this.basePath = config.basePath || 'https://api.emploi-store.fr'
    }


}