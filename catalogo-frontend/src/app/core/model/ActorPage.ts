import { Pageable } from "../model/Pageable";
import { Actor } from "../../actor/model/Actor";

export class ActorPage {
  content!: Actor[];
  pageable!: Pageable;
  totalElements!:number;
}
