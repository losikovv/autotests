package ru.instamart.kraken.service.testit;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Slf4j
public final class MigrateCaseId {

    private static final String URL = "https://testit.sbmt.io";
    private static final String PRIVATE_TOKEN = "PRIVATE_TOKEN";
    private static final List<ProjectData> pds = new ArrayList<>();

    static {
        String DS = " ";
        String SURGE = " ";
        String PB = " ";
        String CS = " ";
        String ODFS = " ";
        String DSPTCH = " ";
        String ODW = " ";
        String ETA = " ";
        String SHPCALC = " ";
        String ODSC = " ";
        String RES = " ";
        String SHAPI = " ";
        String INAPI = " ";
        String FNS = " ";
        String STS = " ";
        String TM = " ";
        String SA = " ";
        String STF = "0786419d-a70a-4f16-b780-4ef668a81d00";
        String B2B = "c6c2027e-b360-4ac3-8f6b-a366908404d0";
        String PM = "196f2d3a-3ed1-41b0-8749-31fe4d320f85";
        String DEBUG = " ";
        pds.add(ProjectData.builder()
                .UUID(B2B) //B2B
                .path("web-tests/reforged/src/test/java/ru/instamart/test/reforged/business")
                .build());
        pds.add(ProjectData.builder()
                .UUID(PM) //PM
                .path("web-tests/reforged/src/test/java/ru/instamart/test/reforged/hp_ops_partners")
                .build());
        pds.add(ProjectData.builder()
                .UUID(STF) //STF
                .path("web-tests/reforged/src/test/java/ru/instamart/test/reforged/selgros")
                .build());
        pds.add(ProjectData.builder()
                .UUID(STF) //STF
                .path("web-tests/reforged/src/test/java/ru/instamart/test/reforged/stf")
                .build());
        pds.add(ProjectData.builder()
                .UUID(STF) //STF
                .path("web-tests/reforged/src/test/java/ru/instamart/test/reforged/stf_prod")
                .build());
    }

    private MigrateCaseId() {}

    public static void main(String[] args) throws Exception {
        final var migrator = new Migrator(new TestItService().getTestItClient(), new FileReader(), new FileParser(), new FileWriter());
        migrator.migrate();
    }

    public static final class FileReader {
        public Set<File> read(final String path) {
            try(final var stream = Files.walk(Paths.get(path))) {
                return stream.filter(Files::isRegularFile)
                        .map(Path::toFile)
                        .collect(Collectors.toSet());
            } catch (IOException e) {
                log.error("Can't read file", e);
            }
            return Collections.emptySet();
        }
    }

    public static final class FileParser {
        private static final Pattern tms = Pattern.compile("@TmsLink\\(\"(.*?)\"\\)");

        public List<String> parse(final File file) {
            final var tmsLinks = new ArrayList<String>();
            try(final var br = new BufferedReader(new java.io.FileReader(file))) {
                String st;
                while (nonNull(st = br.readLine())) {
                    final var matcher = tms.matcher(st);
                    while (matcher.find()) {
                        tmsLinks.add(matcher.group(1));
                    }
                }
            } catch (IOException e) {
                log.error("Can't parse file={}", file.getName(), e);
            }
            return tmsLinks;
        }
    }

    public static final class TestItService {
        @Getter
        private final TestItClient testItClient;

        public TestItService() {
            this.testItClient = new TestItClient(URL, PRIVATE_TOKEN);
        }
    }

    public static final class FileWriter {
        public void write(final File file, final String from, final String to) {
            try {
                var content = Files.readString(file.toPath());
                content = content.replaceAll(from, to);
                Files.write(file.toPath(), content.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                log.error("Write file={} exception for replacing from={} to={}", file.getName(), from, to, e);
            }
        }
    }

    @RequiredArgsConstructor
    public static final class Migrator {
        private final TestItClient testItClient;
        private final FileReader fileReader;
        private final FileParser fileParser;
        private final FileWriter fileWriter;

        public void migrate() throws Exception {
            if (PRIVATE_TOKEN.equals("PRIVATE_TOKEN")) {
                throw new Exception("Вставь свой токен по братски");
            }
            pds.forEach(pd -> {
                final var workItems = testItClient.getWorkItems(pd.UUID);
                final var files = fileReader.read(pd.getPath());
                files.forEach(file -> {
                    final var oldIds = fileParser.parse(file);
                    workItems.forEach(workItem -> update(workItem.getName(), workItem.getGlobalId(), oldIds, file));
                });
            });
        }

        private void update(final String name, final Long newId, final List<String> ids, final File file) {
            ids.forEach(id -> {
                //Что бы было более точное соответствие (пример: STF-12.)
                if (name.contains("-"+id+".")) {
                    log.debug("Update id for testcase={}, from id={} to={}", name, id, newId);
                    fileWriter.write(file, id, String.valueOf(newId));
                }
            });
        }
    }

    @Data
    @Builder
    private static final class ProjectData {
        private final String UUID; //UUID проекта в testit
        private final String path; //Относительный путь к пакету с тестами

        //Получаем абсолютный путь из относительного
        public String getPath() {
            return FileSystems
                    .getDefault()
                    .getPath(path)
                    .normalize()
                    .toAbsolutePath()
                    .toString();
        }
    }
}
