import { CategoryModel } from './category.model';

export interface SelectedCategoryModel {
  category: CategoryModel;
  topics: string[];
}
