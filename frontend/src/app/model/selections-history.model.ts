import {ProductModel} from "./product.model";

export interface SelectionsHistoryModel {
  transactionId: number;
  name: string;
  url: string;
  photo_100: string;
  gifts: ProductModel[];
}
