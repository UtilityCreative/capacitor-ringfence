declare module '@capacitor/core' {
  interface PluginRegistry {
    RingfencePlugin: RingfencePluginPlugin;
  }
}

export interface RingfencePluginPlugin {
  enableUserLocation(): void;
  passJson(options: { jsonPassed: string }): void
}



