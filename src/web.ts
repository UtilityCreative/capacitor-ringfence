import { WebPlugin } from '@capacitor/core';
import { RingfencePluginPlugin } from './definitions';


export class RingfencePluginWeb extends WebPlugin implements RingfencePluginPlugin {
  constructor() {
    super({
      name: 'RingfencePlugin',
      platforms: ['web'],
    });
  }

  async enableUserLocation(){}

  // async passJson(jsonString:object) {
  async passJson(_: { jsonPassed: string }) {
    // This web equivelant function isn't used (because geofence only works within native plugin) however it must
    // must remain here as an empty function because it is still called when in web context
  }
}

const RingfencePlugin = new RingfencePluginWeb();

export { RingfencePlugin };

import { registerWebPlugin } from '@capacitor/core';
registerWebPlugin(RingfencePlugin);
