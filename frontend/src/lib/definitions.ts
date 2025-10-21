enum Evaluation{
    DS,EXAM,ORALE,TP
}

enum TypeIntervention {
    COURS, TD,TP,PROJET_TUTORE
}

type Affectation={
    id:number,
    type: TypeIntervention
}

//TODO: make the role enum
type User = {
    cin:string,
    nom: string
    role: string,
}

type Filliere = {
    id:number,
    nom:string,
    niveau:string // A big enum from LX and its derivatives L1_INFO, L1_TIC to Masters and ING_X and their derivatives
}

type Etudiant = User & {
    nce: string,
    notes: Note[]
    filliere: Filliere
}

type Enseignant = User & {
    affectations: Affectation[]
}

type TypeEval={
    id: number,
    libelle: Evaluation,
    coef: number
}

type Matiere={
    id: number,
    nom: string,
    coef: number,
    Notes: Note[] | null
}

type Note={
    id:number,
    value:number,
    dateSaisi: Date,
    type: TypeEval,
    matiere: string /*It should be Matiere but due to time constraints, it's string*/
}

