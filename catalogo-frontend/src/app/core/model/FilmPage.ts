import { Film } from "../../film/model/Film";
import { Pageable } from "./Pageable";

export class FilmPage {
  content!: Film[];
    pageable!: Pageable;
    totalElements!:number;
}
