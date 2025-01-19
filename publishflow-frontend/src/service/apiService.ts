interface ApiResponse<T> {
  data: T[];
  error?: string;
}

async function fetchData<T>(
  url: string,
  baseOptions = {} as RequestInit
): Promise<ApiResponse<T>> {
  const options = {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
    ...baseOptions,
  };
  try {
    const response = await fetch(url, options);

    if (!response.ok) {
      throw new Error(`Request failed with status ${response.status}`);
    }

    const data: T[] = await response.json();
    return { data };
  } catch (error) {
    return {
      error: (error as { message: string }).message,
      data: [{}] as T[],
    };
  }
}

export { fetchData };
