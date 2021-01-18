import { WebPlugin } from '@capacitor/core';
import { RingfencePluginPlugin } from './definitions';
export declare class RingfencePluginWeb extends WebPlugin implements RingfencePluginPlugin {
    constructor();
    enableUserLocation(): Promise<void>;
    passJson(_: {
        jsonPassed: string;
    }): Promise<void>;
}
declare const RingfencePlugin: RingfencePluginWeb;
export { RingfencePlugin };
