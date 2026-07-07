export const environment = {
    apiUrl: (window as any).__env?.apiUrl ?? 'http://localhost:8085/api',
    name: 'prod'
};
