import {Base} from "./base";
import {applyMixins} from "./utils";
import {Offres} from "./offresdemploi";
import {Partenaire} from "./partner";


class PoleEmploi extends Base {
}

interface PoleEmploi extends Offres, Partenaire {
}

applyMixins(PoleEmploi, [Offres, Partenaire])
export default PoleEmploi