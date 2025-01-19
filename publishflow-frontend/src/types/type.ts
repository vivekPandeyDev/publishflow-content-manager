import { LoaderFunction } from "react-router-dom";

type MyParam = {
  id: string;
};

export type ActionType<T = MyParam> = {
  request: Request;
  params: T;
};

export type LoaderData<TLoaderFn extends LoaderFunction> = Awaited<
  ReturnType<TLoaderFn>
> extends Response | infer D
  ? D
  : never;
