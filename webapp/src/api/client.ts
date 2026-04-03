const defaultSpringUrl = "http://localhost:8081";

const BASE_URL = defaultSpringUrl;

export async function apiPost<T>(path: string, body: unknown): Promise<T> {
    const res = await fetch(`${BASE_URL}${path}`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(body),
    })

    if (!res.ok) {
        const errorBody = await res.json().catch(() => ({ error: 'Something went wrong' }))
        throw new Error(errorBody.error ?? 'Something went wrong')
    }

    return res.json();
}

export async function apiGet<T>(path: string): Promise<T> {
    const res = await fetch(`${BASE_URL}${path}`)
    if (!res.ok) throw new Error(await res.text())
    return res.json();
}