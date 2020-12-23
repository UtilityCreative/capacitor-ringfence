import { WebPlugin } from '@capacitor/core';
import { RingfencePluginPlugin } from './definitions';

export class RingfencePluginWeb extends WebPlugin implements RingfencePluginPlugin {
  constructor() {
    super({
      name: 'RingfencePlugin',
      platforms: ['web'],
    });
  }

  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
  async enableUserLocation(){}
/*
  async getContacts(filter:String): Promise<{results: any[] }> {
    console.log('filter: ', filter);
    return {
      results: [{
        firstName: 'Dummy',
        lastName: 'Entry',
        telephone: '123456'
      }]
    }
  }
  */
}

const RingfencePlugin = new RingfencePluginWeb();

export { RingfencePlugin };

import { registerWebPlugin } from '@capacitor/core';
registerWebPlugin(RingfencePlugin);
