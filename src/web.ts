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

  jsonURL = "https://api.jsonbin.io/v3/b/600033fb68f9f835a3df124a/latest>"
  async passJson(jsonString:String) {
    // This web equivelant function isn't used (because geofence only works within native plugin) however it must
    // must remain here as an empty function because it is still called when in web context
    console.log(jsonString);
  }
}

const RingfencePlugin = new RingfencePluginWeb();

export { RingfencePlugin };

import { registerWebPlugin } from '@capacitor/core';
registerWebPlugin(RingfencePlugin);
