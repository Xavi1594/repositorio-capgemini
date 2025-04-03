import { Language } from "../../language/model/Language";

export class Film {
  id?: number;
  titulo!: string;
  idioma!: Language;
  periodo?: number;
  precio?: number;
  sancion?: number;
  coste?:number;
  descripcion!: string;
  // rating?: string;
  // languageVOId?: number;
  // actores!: string[];
  // lanzamiento?: number;


}
