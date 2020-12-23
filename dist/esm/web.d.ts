import { WebPlugin } from '@capacitor/core';
import { RingfencePluginPlugin } from './definitions';
export declare class RingfencePluginWeb extends WebPlugin implements RingfencePluginPlugin {
    constructor();
    echo(options: {
        value: string;
    }): Promise<{
        value: string;
    }>;
    enableUserLocation(): Promise<void>;
}
declare const RingfencePlugin: RingfencePluginWeb;
export { RingfencePlugin };
