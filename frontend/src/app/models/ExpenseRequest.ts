import { ExpenseCategory } from "./enums/ExpenseCategory";

export interface ExpenseRequest {
  name: string;
  description?: string;
  amount: number;
  date: string;
  expenseCategory: ExpenseCategory;
}