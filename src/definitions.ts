declare module '@capacitor/core' {
  interface PluginRegistry {
    RingfencePlugin: RingfencePluginPlugin;
  }
}

export interface RingfencePluginPlugin {
  enableUserLocation(): void;
  //getContacts(filter: string): Promise<{results: any[]}>
  passJson(jsonString: string): void
}
