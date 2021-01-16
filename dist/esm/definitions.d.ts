declare module '@capacitor/core' {
    interface PluginRegistry {
        RingfencePlugin: RingfencePluginPlugin;
    }
}
export interface RingfencePluginPlugin {
    echo(options: {
        value: string;
    }): Promise<{
        value: string;
    }>;
    enableUserLocation(): void;
    passJson(jsonString: string): void;
}
