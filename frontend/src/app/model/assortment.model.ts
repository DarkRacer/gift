import { SelectedCategoryModel } from './selected-category.model';
import { ProductModel } from './product.model';

export interface AssortmentModel {
  category: SelectedCategoryModel;
  products: ProductModel[];
}
