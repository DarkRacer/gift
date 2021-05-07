import { CategoryModel } from './category.model';

export interface CategoryWordsModel {
  word: string;
  categories: CategoryModel[];
}
