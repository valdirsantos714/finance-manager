import { ExpenseResponse } from "./ExpenseResponse";
import { IncomeResponse } from "./IncomeResponse";

export interface UserResponse {
  id: number;
  name: string;
  email: string;
  password: string;
  incomes: IncomeResponse[];
  expenses: ExpenseResponse[];
}