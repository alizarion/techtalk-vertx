export type CodeROME = {
    code: string,
    libelle: string,
    codeDepartement: string,
    codePostal: string
}

export enum ModeSelectionPartenaires {
    INCLUS,
    EXCLU
}

export type SearchOffresParams = {
    theme: Number,
    grandDomaine: string,
    natureContrat: string,
    domaine: string,
    codeROME: string,
    sort: Number,
    appellation: string,
    secteurActivite: string,
    experience: Number,
    typeContrat: string,
    range: string,
    origineOffre: Number,
    qualification: Number,
    tempsPlein: boolean,
    commune: string,
    distance: Number,
    departement: string,
    inclureLimitrophes: boolean,
    region: string,
    paysContinent: string,
    niveauFormation: string,
    permis: string,
    motsCles: string,
    salaireMin: Number,
    periodeSalaire: string,
    accesTravailleurHandicape: boolean,
    offresMRS: boolean,
    experienceExigence: string,
    publieeDepuis: Number,
    minCreationDate: Date,
    maxCreationDate: Date,
    modeSelectionPartenaires: ModeSelectionPartenaires,
    partenaires: string,
    dureeHebdoMin: Number,
    dureeHebdoMax: Number
}
