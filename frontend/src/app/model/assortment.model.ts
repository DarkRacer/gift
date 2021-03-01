import { CategoryModel } from './category.model';
import { ProductModel } from './product.model';

export interface AssortmentModel {
  category: CategoryModel;
  products: ProductModel[];
}
