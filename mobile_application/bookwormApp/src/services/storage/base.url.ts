const localhost = "http://127.0.0.1:9999/library/api";
const production = "";

export const BASE_URL: string = process.env.NODE_ENV === "production" ?  production : localhost;