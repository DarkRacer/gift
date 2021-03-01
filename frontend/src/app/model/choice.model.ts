import { ProductModel } from './product.model';

export interface ChoiceModel {
  recipient: string;
  products: ProductModel[];
}
