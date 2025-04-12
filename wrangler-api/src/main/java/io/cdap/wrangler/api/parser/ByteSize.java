/*
 * Copyright © 2017-2019 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

/*
 * Copyright © 2017-2019 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.cdap.wrangler.api.parser;

public class ByteSize extends Token {
    private final long bytes;

    public ByteSize(String value) {
        super(TokenType.BYTE_SIZE, value);
        this.bytes = parseByteSize(value);
    }

    public long getBytes() {
        return bytes;
    }

    private long parseByteSize(String str) {
        String numStr = str.replaceAll("[^0-9.]", "");
        String unitStr = str.replaceAll("[^a-zA-Z]", "").toUpperCase();

        double num = Double.parseDouble(numStr);

        switch (unitStr) {
            case "B":
                return (long) num;
            case "KB":
                return (long) (num * 1024);
            case "MB":
                return (long) (num * 1024 * 1024);
            case "GB":
                return (long) (num * 1024 * 1024 * 1024);
            case "TB":
                return (long) (num * 1024 * 1024 * 1024 * 1024);
            default:
                throw new IllegalArgumentException("Invalid byte size unit: " + unitStr);
        }
    }
}