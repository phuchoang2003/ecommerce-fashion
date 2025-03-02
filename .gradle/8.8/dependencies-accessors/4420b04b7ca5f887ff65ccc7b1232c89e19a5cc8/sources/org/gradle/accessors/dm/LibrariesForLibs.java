package org.gradle.accessors.dm;

import org.gradle.api.NonNullApi;
import org.gradle.api.artifacts.MinimalExternalModuleDependency;
import org.gradle.plugin.use.PluginDependency;
import org.gradle.api.artifacts.ExternalModuleDependencyBundle;
import org.gradle.api.artifacts.MutableVersionConstraint;
import org.gradle.api.provider.Provider;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.ProviderFactory;
import org.gradle.api.internal.catalog.AbstractExternalDependencyFactory;
import org.gradle.api.internal.catalog.DefaultVersionCatalog;
import java.util.Map;
import org.gradle.api.internal.attributes.ImmutableAttributesFactory;
import org.gradle.api.internal.artifacts.dsl.CapabilityNotationParser;
import javax.inject.Inject;

/**
 * A catalog of dependencies accessible via the {@code libs} extension.
 */
@NonNullApi
public class LibrariesForLibs extends AbstractExternalDependencyFactory {

    private final AbstractExternalDependencyFactory owner = this;
    private final ComLibraryAccessors laccForComLibraryAccessors = new ComLibraryAccessors(owner);
    private final IoLibraryAccessors laccForIoLibraryAccessors = new IoLibraryAccessors(owner);
    private final OrgLibraryAccessors laccForOrgLibraryAccessors = new OrgLibraryAccessors(owner);
    private final VersionAccessors vaccForVersionAccessors = new VersionAccessors(providers, config);
    private final BundleAccessors baccForBundleAccessors = new BundleAccessors(objects, providers, config, attributesFactory, capabilityNotationParser);
    private final PluginAccessors paccForPluginAccessors = new PluginAccessors(providers, config);

    @Inject
    public LibrariesForLibs(DefaultVersionCatalog config, ProviderFactory providers, ObjectFactory objects, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) {
        super(config, providers, objects, attributesFactory, capabilityNotationParser);
    }

    /**
     * Group of libraries at <b>com</b>
     */
    public ComLibraryAccessors getCom() {
        return laccForComLibraryAccessors;
    }

    /**
     * Group of libraries at <b>io</b>
     */
    public IoLibraryAccessors getIo() {
        return laccForIoLibraryAccessors;
    }

    /**
     * Group of libraries at <b>org</b>
     */
    public OrgLibraryAccessors getOrg() {
        return laccForOrgLibraryAccessors;
    }

    /**
     * Group of versions at <b>versions</b>
     */
    public VersionAccessors getVersions() {
        return vaccForVersionAccessors;
    }

    /**
     * Group of bundles at <b>bundles</b>
     */
    public BundleAccessors getBundles() {
        return baccForBundleAccessors;
    }

    /**
     * Group of plugins at <b>plugins</b>
     */
    public PluginAccessors getPlugins() {
        return paccForPluginAccessors;
    }

    public static class ComLibraryAccessors extends SubDependencyFactory {
        private final ComCorundumstudioLibraryAccessors laccForComCorundumstudioLibraryAccessors = new ComCorundumstudioLibraryAccessors(owner);
        private final ComGoogleLibraryAccessors laccForComGoogleLibraryAccessors = new ComGoogleLibraryAccessors(owner);
        private final ComMailgunLibraryAccessors laccForComMailgunLibraryAccessors = new ComMailgunLibraryAccessors(owner);
        private final ComMailjetLibraryAccessors laccForComMailjetLibraryAccessors = new ComMailjetLibraryAccessors(owner);
        private final ComStripeLibraryAccessors laccForComStripeLibraryAccessors = new ComStripeLibraryAccessors(owner);
        private final ComVladmihalceaLibraryAccessors laccForComVladmihalceaLibraryAccessors = new ComVladmihalceaLibraryAccessors(owner);

        public ComLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>com.corundumstudio</b>
         */
        public ComCorundumstudioLibraryAccessors getCorundumstudio() {
            return laccForComCorundumstudioLibraryAccessors;
        }

        /**
         * Group of libraries at <b>com.google</b>
         */
        public ComGoogleLibraryAccessors getGoogle() {
            return laccForComGoogleLibraryAccessors;
        }

        /**
         * Group of libraries at <b>com.mailgun</b>
         */
        public ComMailgunLibraryAccessors getMailgun() {
            return laccForComMailgunLibraryAccessors;
        }

        /**
         * Group of libraries at <b>com.mailjet</b>
         */
        public ComMailjetLibraryAccessors getMailjet() {
            return laccForComMailjetLibraryAccessors;
        }

        /**
         * Group of libraries at <b>com.stripe</b>
         */
        public ComStripeLibraryAccessors getStripe() {
            return laccForComStripeLibraryAccessors;
        }

        /**
         * Group of libraries at <b>com.vladmihalcea</b>
         */
        public ComVladmihalceaLibraryAccessors getVladmihalcea() {
            return laccForComVladmihalceaLibraryAccessors;
        }

    }

    public static class ComCorundumstudioLibraryAccessors extends SubDependencyFactory {
        private final ComCorundumstudioSocketioLibraryAccessors laccForComCorundumstudioSocketioLibraryAccessors = new ComCorundumstudioSocketioLibraryAccessors(owner);

        public ComCorundumstudioLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>com.corundumstudio.socketio</b>
         */
        public ComCorundumstudioSocketioLibraryAccessors getSocketio() {
            return laccForComCorundumstudioSocketioLibraryAccessors;
        }

    }

    public static class ComCorundumstudioSocketioLibraryAccessors extends SubDependencyFactory {
        private final ComCorundumstudioSocketioNettyLibraryAccessors laccForComCorundumstudioSocketioNettyLibraryAccessors = new ComCorundumstudioSocketioNettyLibraryAccessors(owner);

        public ComCorundumstudioSocketioLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>com.corundumstudio.socketio.netty</b>
         */
        public ComCorundumstudioSocketioNettyLibraryAccessors getNetty() {
            return laccForComCorundumstudioSocketioNettyLibraryAccessors;
        }

    }

    public static class ComCorundumstudioSocketioNettyLibraryAccessors extends SubDependencyFactory {

        public ComCorundumstudioSocketioNettyLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>socketio</b> with <b>com.corundumstudio.socketio:netty-socketio</b> coordinates and
         * with version reference <b>com.corundumstudio.socketio.netty.socketio</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getSocketio() {
            return create("com.corundumstudio.socketio.netty.socketio");
        }

    }

    public static class ComGoogleLibraryAccessors extends SubDependencyFactory {
        private final ComGoogleCodeLibraryAccessors laccForComGoogleCodeLibraryAccessors = new ComGoogleCodeLibraryAccessors(owner);
        private final ComGoogleFirebaseLibraryAccessors laccForComGoogleFirebaseLibraryAccessors = new ComGoogleFirebaseLibraryAccessors(owner);

        public ComGoogleLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>com.google.code</b>
         */
        public ComGoogleCodeLibraryAccessors getCode() {
            return laccForComGoogleCodeLibraryAccessors;
        }

        /**
         * Group of libraries at <b>com.google.firebase</b>
         */
        public ComGoogleFirebaseLibraryAccessors getFirebase() {
            return laccForComGoogleFirebaseLibraryAccessors;
        }

    }

    public static class ComGoogleCodeLibraryAccessors extends SubDependencyFactory {
        private final ComGoogleCodeGsonLibraryAccessors laccForComGoogleCodeGsonLibraryAccessors = new ComGoogleCodeGsonLibraryAccessors(owner);

        public ComGoogleCodeLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>com.google.code.gson</b>
         */
        public ComGoogleCodeGsonLibraryAccessors getGson() {
            return laccForComGoogleCodeGsonLibraryAccessors;
        }

    }

    public static class ComGoogleCodeGsonLibraryAccessors extends SubDependencyFactory {

        public ComGoogleCodeGsonLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>gson</b> with <b>com.google.code.gson:gson</b> coordinates and
         * with version reference <b>com.google.code.gson.gson</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getGson() {
            return create("com.google.code.gson.gson");
        }

    }

    public static class ComGoogleFirebaseLibraryAccessors extends SubDependencyFactory {
        private final ComGoogleFirebaseFirebaseLibraryAccessors laccForComGoogleFirebaseFirebaseLibraryAccessors = new ComGoogleFirebaseFirebaseLibraryAccessors(owner);

        public ComGoogleFirebaseLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>com.google.firebase.firebase</b>
         */
        public ComGoogleFirebaseFirebaseLibraryAccessors getFirebase() {
            return laccForComGoogleFirebaseFirebaseLibraryAccessors;
        }

    }

    public static class ComGoogleFirebaseFirebaseLibraryAccessors extends SubDependencyFactory {

        public ComGoogleFirebaseFirebaseLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>admin</b> with <b>com.google.firebase:firebase-admin</b> coordinates and
         * with version reference <b>com.google.firebase.firebase.admin</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getAdmin() {
            return create("com.google.firebase.firebase.admin");
        }

    }

    public static class ComMailgunLibraryAccessors extends SubDependencyFactory {
        private final ComMailgunMailgunLibraryAccessors laccForComMailgunMailgunLibraryAccessors = new ComMailgunMailgunLibraryAccessors(owner);

        public ComMailgunLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>com.mailgun.mailgun</b>
         */
        public ComMailgunMailgunLibraryAccessors getMailgun() {
            return laccForComMailgunMailgunLibraryAccessors;
        }

    }

    public static class ComMailgunMailgunLibraryAccessors extends SubDependencyFactory {

        public ComMailgunMailgunLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>java</b> with <b>com.mailgun:mailgun-java</b> coordinates and
         * with version reference <b>com.mailgun.mailgun.java</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJava() {
            return create("com.mailgun.mailgun.java");
        }

    }

    public static class ComMailjetLibraryAccessors extends SubDependencyFactory {
        private final ComMailjetMailjetLibraryAccessors laccForComMailjetMailjetLibraryAccessors = new ComMailjetMailjetLibraryAccessors(owner);

        public ComMailjetLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>com.mailjet.mailjet</b>
         */
        public ComMailjetMailjetLibraryAccessors getMailjet() {
            return laccForComMailjetMailjetLibraryAccessors;
        }

    }

    public static class ComMailjetMailjetLibraryAccessors extends SubDependencyFactory {

        public ComMailjetMailjetLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>client</b> with <b>com.mailjet:mailjet-client</b> coordinates and
         * with version reference <b>com.mailjet.mailjet.client</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getClient() {
            return create("com.mailjet.mailjet.client");
        }

    }

    public static class ComStripeLibraryAccessors extends SubDependencyFactory {
        private final ComStripeStripeLibraryAccessors laccForComStripeStripeLibraryAccessors = new ComStripeStripeLibraryAccessors(owner);

        public ComStripeLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>com.stripe.stripe</b>
         */
        public ComStripeStripeLibraryAccessors getStripe() {
            return laccForComStripeStripeLibraryAccessors;
        }

    }

    public static class ComStripeStripeLibraryAccessors extends SubDependencyFactory {

        public ComStripeStripeLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>java</b> with <b>com.stripe:stripe-java</b> coordinates and
         * with version reference <b>com.stripe.stripe.java</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJava() {
            return create("com.stripe.stripe.java");
        }

    }

    public static class ComVladmihalceaLibraryAccessors extends SubDependencyFactory {
        private final ComVladmihalceaHibernateLibraryAccessors laccForComVladmihalceaHibernateLibraryAccessors = new ComVladmihalceaHibernateLibraryAccessors(owner);

        public ComVladmihalceaLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>com.vladmihalcea.hibernate</b>
         */
        public ComVladmihalceaHibernateLibraryAccessors getHibernate() {
            return laccForComVladmihalceaHibernateLibraryAccessors;
        }

    }

    public static class ComVladmihalceaHibernateLibraryAccessors extends SubDependencyFactory {
        private final ComVladmihalceaHibernateTypesLibraryAccessors laccForComVladmihalceaHibernateTypesLibraryAccessors = new ComVladmihalceaHibernateTypesLibraryAccessors(owner);

        public ComVladmihalceaHibernateLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>com.vladmihalcea.hibernate.types</b>
         */
        public ComVladmihalceaHibernateTypesLibraryAccessors getTypes() {
            return laccForComVladmihalceaHibernateTypesLibraryAccessors;
        }

    }

    public static class ComVladmihalceaHibernateTypesLibraryAccessors extends SubDependencyFactory {

        public ComVladmihalceaHibernateTypesLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>v52</b> with <b>com.vladmihalcea:hibernate-types-52</b> coordinates and
         * with version reference <b>com.vladmihalcea.hibernate.types.v52</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getV52() {
            return create("com.vladmihalcea.hibernate.types.v52");
        }

    }

    public static class IoLibraryAccessors extends SubDependencyFactory {
        private final IoGithubLibraryAccessors laccForIoGithubLibraryAccessors = new IoGithubLibraryAccessors(owner);
        private final IoJsonwebtokenLibraryAccessors laccForIoJsonwebtokenLibraryAccessors = new IoJsonwebtokenLibraryAccessors(owner);
        private final IoMinioLibraryAccessors laccForIoMinioLibraryAccessors = new IoMinioLibraryAccessors(owner);

        public IoLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>io.github</b>
         */
        public IoGithubLibraryAccessors getGithub() {
            return laccForIoGithubLibraryAccessors;
        }

        /**
         * Group of libraries at <b>io.jsonwebtoken</b>
         */
        public IoJsonwebtokenLibraryAccessors getJsonwebtoken() {
            return laccForIoJsonwebtokenLibraryAccessors;
        }

        /**
         * Group of libraries at <b>io.minio</b>
         */
        public IoMinioLibraryAccessors getMinio() {
            return laccForIoMinioLibraryAccessors;
        }

    }

    public static class IoGithubLibraryAccessors extends SubDependencyFactory {
        private final IoGithubLong23112002LibraryAccessors laccForIoGithubLong23112002LibraryAccessors = new IoGithubLong23112002LibraryAccessors(owner);

        public IoGithubLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>io.github.long23112002</b>
         */
        public IoGithubLong23112002LibraryAccessors getLong23112002() {
            return laccForIoGithubLong23112002LibraryAccessors;
        }

    }

    public static class IoGithubLong23112002LibraryAccessors extends SubDependencyFactory {
        private final IoGithubLong23112002CommonLibraryAccessors laccForIoGithubLong23112002CommonLibraryAccessors = new IoGithubLong23112002CommonLibraryAccessors(owner);

        public IoGithubLong23112002LibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>io.github.long23112002.common</b>
         */
        public IoGithubLong23112002CommonLibraryAccessors getCommon() {
            return laccForIoGithubLong23112002CommonLibraryAccessors;
        }

    }

    public static class IoGithubLong23112002CommonLibraryAccessors extends SubDependencyFactory {

        public IoGithubLong23112002CommonLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>service</b> with <b>io.github.long23112002:common-service</b> coordinates and
         * with version reference <b>io.github.long23112002.common.service</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getService() {
            return create("io.github.long23112002.common.service");
        }

    }

    public static class IoJsonwebtokenLibraryAccessors extends SubDependencyFactory {
        private final IoJsonwebtokenJjwtLibraryAccessors laccForIoJsonwebtokenJjwtLibraryAccessors = new IoJsonwebtokenJjwtLibraryAccessors(owner);

        public IoJsonwebtokenLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>io.jsonwebtoken.jjwt</b>
         */
        public IoJsonwebtokenJjwtLibraryAccessors getJjwt() {
            return laccForIoJsonwebtokenJjwtLibraryAccessors;
        }

    }

    public static class IoJsonwebtokenJjwtLibraryAccessors extends SubDependencyFactory {

        public IoJsonwebtokenJjwtLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>api</b> with <b>io.jsonwebtoken:jjwt-api</b> coordinates and
         * with version reference <b>io.jsonwebtoken.jjwt.api</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getApi() {
            return create("io.jsonwebtoken.jjwt.api");
        }

        /**
         * Dependency provider for <b>impl</b> with <b>io.jsonwebtoken:jjwt-impl</b> coordinates and
         * with version reference <b>io.jsonwebtoken.jjwt.impl</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getImpl() {
            return create("io.jsonwebtoken.jjwt.impl");
        }

        /**
         * Dependency provider for <b>jackson</b> with <b>io.jsonwebtoken:jjwt-jackson</b> coordinates and
         * with version reference <b>io.jsonwebtoken.jjwt.jackson</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJackson() {
            return create("io.jsonwebtoken.jjwt.jackson");
        }

    }

    public static class IoMinioLibraryAccessors extends SubDependencyFactory {

        public IoMinioLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>minio</b> with <b>io.minio:minio</b> coordinates and
         * with version reference <b>io.minio.minio</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getMinio() {
            return create("io.minio.minio");
        }

    }

    public static class OrgLibraryAccessors extends SubDependencyFactory {
        private final OrgModelmapperLibraryAccessors laccForOrgModelmapperLibraryAccessors = new OrgModelmapperLibraryAccessors(owner);
        private final OrgPostgresqlLibraryAccessors laccForOrgPostgresqlLibraryAccessors = new OrgPostgresqlLibraryAccessors(owner);
        private final OrgProjectlombokLibraryAccessors laccForOrgProjectlombokLibraryAccessors = new OrgProjectlombokLibraryAccessors(owner);
        private final OrgSpringframeworkLibraryAccessors laccForOrgSpringframeworkLibraryAccessors = new OrgSpringframeworkLibraryAccessors(owner);

        public OrgLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>org.modelmapper</b>
         */
        public OrgModelmapperLibraryAccessors getModelmapper() {
            return laccForOrgModelmapperLibraryAccessors;
        }

        /**
         * Group of libraries at <b>org.postgresql</b>
         */
        public OrgPostgresqlLibraryAccessors getPostgresql() {
            return laccForOrgPostgresqlLibraryAccessors;
        }

        /**
         * Group of libraries at <b>org.projectlombok</b>
         */
        public OrgProjectlombokLibraryAccessors getProjectlombok() {
            return laccForOrgProjectlombokLibraryAccessors;
        }

        /**
         * Group of libraries at <b>org.springframework</b>
         */
        public OrgSpringframeworkLibraryAccessors getSpringframework() {
            return laccForOrgSpringframeworkLibraryAccessors;
        }

    }

    public static class OrgModelmapperLibraryAccessors extends SubDependencyFactory {

        public OrgModelmapperLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>modelmapper</b> with <b>org.modelmapper:modelmapper</b> coordinates and
         * with version reference <b>org.modelmapper.modelmapper</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getModelmapper() {
            return create("org.modelmapper.modelmapper");
        }

    }

    public static class OrgPostgresqlLibraryAccessors extends SubDependencyFactory {

        public OrgPostgresqlLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>postgresql</b> with <b>org.postgresql:postgresql</b> coordinates and
         * with version reference <b>org.postgresql.postgresql</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getPostgresql() {
            return create("org.postgresql.postgresql");
        }

    }

    public static class OrgProjectlombokLibraryAccessors extends SubDependencyFactory {

        public OrgProjectlombokLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>lombok</b> with <b>org.projectlombok:lombok</b> coordinates and
         * with version reference <b>org.projectlombok.lombok</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getLombok() {
            return create("org.projectlombok.lombok");
        }

    }

    public static class OrgSpringframeworkLibraryAccessors extends SubDependencyFactory {
        private final OrgSpringframeworkBootLibraryAccessors laccForOrgSpringframeworkBootLibraryAccessors = new OrgSpringframeworkBootLibraryAccessors(owner);
        private final OrgSpringframeworkCloudLibraryAccessors laccForOrgSpringframeworkCloudLibraryAccessors = new OrgSpringframeworkCloudLibraryAccessors(owner);
        private final OrgSpringframeworkRetryLibraryAccessors laccForOrgSpringframeworkRetryLibraryAccessors = new OrgSpringframeworkRetryLibraryAccessors(owner);

        public OrgSpringframeworkLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>org.springframework.boot</b>
         */
        public OrgSpringframeworkBootLibraryAccessors getBoot() {
            return laccForOrgSpringframeworkBootLibraryAccessors;
        }

        /**
         * Group of libraries at <b>org.springframework.cloud</b>
         */
        public OrgSpringframeworkCloudLibraryAccessors getCloud() {
            return laccForOrgSpringframeworkCloudLibraryAccessors;
        }

        /**
         * Group of libraries at <b>org.springframework.retry</b>
         */
        public OrgSpringframeworkRetryLibraryAccessors getRetry() {
            return laccForOrgSpringframeworkRetryLibraryAccessors;
        }

    }

    public static class OrgSpringframeworkBootLibraryAccessors extends SubDependencyFactory {
        private final OrgSpringframeworkBootSpringLibraryAccessors laccForOrgSpringframeworkBootSpringLibraryAccessors = new OrgSpringframeworkBootSpringLibraryAccessors(owner);

        public OrgSpringframeworkBootLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>org.springframework.boot.spring</b>
         */
        public OrgSpringframeworkBootSpringLibraryAccessors getSpring() {
            return laccForOrgSpringframeworkBootSpringLibraryAccessors;
        }

    }

    public static class OrgSpringframeworkBootSpringLibraryAccessors extends SubDependencyFactory {
        private final OrgSpringframeworkBootSpringBootLibraryAccessors laccForOrgSpringframeworkBootSpringBootLibraryAccessors = new OrgSpringframeworkBootSpringBootLibraryAccessors(owner);

        public OrgSpringframeworkBootSpringLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>org.springframework.boot.spring.boot</b>
         */
        public OrgSpringframeworkBootSpringBootLibraryAccessors getBoot() {
            return laccForOrgSpringframeworkBootSpringBootLibraryAccessors;
        }

    }

    public static class OrgSpringframeworkBootSpringBootLibraryAccessors extends SubDependencyFactory {
        private final OrgSpringframeworkBootSpringBootStarterLibraryAccessors laccForOrgSpringframeworkBootSpringBootStarterLibraryAccessors = new OrgSpringframeworkBootSpringBootStarterLibraryAccessors(owner);

        public OrgSpringframeworkBootSpringBootLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>org.springframework.boot.spring.boot.starter</b>
         */
        public OrgSpringframeworkBootSpringBootStarterLibraryAccessors getStarter() {
            return laccForOrgSpringframeworkBootSpringBootStarterLibraryAccessors;
        }

    }

    public static class OrgSpringframeworkBootSpringBootStarterLibraryAccessors extends SubDependencyFactory {
        private final OrgSpringframeworkBootSpringBootStarterDataLibraryAccessors laccForOrgSpringframeworkBootSpringBootStarterDataLibraryAccessors = new OrgSpringframeworkBootSpringBootStarterDataLibraryAccessors(owner);

        public OrgSpringframeworkBootSpringBootStarterLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>test</b> with <b>org.springframework.boot:spring-boot-starter-test</b> coordinates and
         * with version reference <b>org.springframework.boot.spring.boot.starter.test</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getTest() {
            return create("org.springframework.boot.spring.boot.starter.test");
        }

        /**
         * Dependency provider for <b>validation</b> with <b>org.springframework.boot:spring-boot-starter-validation</b> coordinates and
         * with version reference <b>org.springframework.boot.spring.boot.starter.validation</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getValidation() {
            return create("org.springframework.boot.spring.boot.starter.validation");
        }

        /**
         * Dependency provider for <b>web</b> with <b>org.springframework.boot:spring-boot-starter-web</b> coordinates and
         * with version reference <b>org.springframework.boot.spring.boot.starter.web</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getWeb() {
            return create("org.springframework.boot.spring.boot.starter.web");
        }

        /**
         * Group of libraries at <b>org.springframework.boot.spring.boot.starter.data</b>
         */
        public OrgSpringframeworkBootSpringBootStarterDataLibraryAccessors getData() {
            return laccForOrgSpringframeworkBootSpringBootStarterDataLibraryAccessors;
        }

    }

    public static class OrgSpringframeworkBootSpringBootStarterDataLibraryAccessors extends SubDependencyFactory {

        public OrgSpringframeworkBootSpringBootStarterDataLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>jpa</b> with <b>org.springframework.boot:spring-boot-starter-data-jpa</b> coordinates and
         * with version reference <b>org.springframework.boot.spring.boot.starter.data.jpa</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJpa() {
            return create("org.springframework.boot.spring.boot.starter.data.jpa");
        }

        /**
         * Dependency provider for <b>mongodb</b> with <b>org.springframework.boot:spring-boot-starter-data-mongodb</b> coordinates and
         * with version reference <b>org.springframework.boot.spring.boot.starter.data.mongodb</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getMongodb() {
            return create("org.springframework.boot.spring.boot.starter.data.mongodb");
        }

        /**
         * Dependency provider for <b>redis</b> with <b>org.springframework.boot:spring-boot-starter-data-redis</b> coordinates and
         * with version reference <b>org.springframework.boot.spring.boot.starter.data.redis</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getRedis() {
            return create("org.springframework.boot.spring.boot.starter.data.redis");
        }

    }

    public static class OrgSpringframeworkCloudLibraryAccessors extends SubDependencyFactory {
        private final OrgSpringframeworkCloudSpringLibraryAccessors laccForOrgSpringframeworkCloudSpringLibraryAccessors = new OrgSpringframeworkCloudSpringLibraryAccessors(owner);

        public OrgSpringframeworkCloudLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>org.springframework.cloud.spring</b>
         */
        public OrgSpringframeworkCloudSpringLibraryAccessors getSpring() {
            return laccForOrgSpringframeworkCloudSpringLibraryAccessors;
        }

    }

    public static class OrgSpringframeworkCloudSpringLibraryAccessors extends SubDependencyFactory {
        private final OrgSpringframeworkCloudSpringCloudLibraryAccessors laccForOrgSpringframeworkCloudSpringCloudLibraryAccessors = new OrgSpringframeworkCloudSpringCloudLibraryAccessors(owner);

        public OrgSpringframeworkCloudSpringLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>org.springframework.cloud.spring.cloud</b>
         */
        public OrgSpringframeworkCloudSpringCloudLibraryAccessors getCloud() {
            return laccForOrgSpringframeworkCloudSpringCloudLibraryAccessors;
        }

    }

    public static class OrgSpringframeworkCloudSpringCloudLibraryAccessors extends SubDependencyFactory {
        private final OrgSpringframeworkCloudSpringCloudStarterLibraryAccessors laccForOrgSpringframeworkCloudSpringCloudStarterLibraryAccessors = new OrgSpringframeworkCloudSpringCloudStarterLibraryAccessors(owner);

        public OrgSpringframeworkCloudSpringCloudLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>org.springframework.cloud.spring.cloud.starter</b>
         */
        public OrgSpringframeworkCloudSpringCloudStarterLibraryAccessors getStarter() {
            return laccForOrgSpringframeworkCloudSpringCloudStarterLibraryAccessors;
        }

    }

    public static class OrgSpringframeworkCloudSpringCloudStarterLibraryAccessors extends SubDependencyFactory {

        public OrgSpringframeworkCloudSpringCloudStarterLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>openfeign</b> with <b>org.springframework.cloud:spring-cloud-starter-openfeign</b> coordinates and
         * with version reference <b>org.springframework.cloud.spring.cloud.starter.openfeign</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getOpenfeign() {
            return create("org.springframework.cloud.spring.cloud.starter.openfeign");
        }

    }

    public static class OrgSpringframeworkRetryLibraryAccessors extends SubDependencyFactory {
        private final OrgSpringframeworkRetrySpringLibraryAccessors laccForOrgSpringframeworkRetrySpringLibraryAccessors = new OrgSpringframeworkRetrySpringLibraryAccessors(owner);

        public OrgSpringframeworkRetryLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>org.springframework.retry.spring</b>
         */
        public OrgSpringframeworkRetrySpringLibraryAccessors getSpring() {
            return laccForOrgSpringframeworkRetrySpringLibraryAccessors;
        }

    }

    public static class OrgSpringframeworkRetrySpringLibraryAccessors extends SubDependencyFactory {

        public OrgSpringframeworkRetrySpringLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>retry</b> with <b>org.springframework.retry:spring-retry</b> coordinates and
         * with version reference <b>org.springframework.retry.spring.retry</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getRetry() {
            return create("org.springframework.retry.spring.retry");
        }

    }

    public static class VersionAccessors extends VersionFactory  {

        private final ComVersionAccessors vaccForComVersionAccessors = new ComVersionAccessors(providers, config);
        private final IoVersionAccessors vaccForIoVersionAccessors = new IoVersionAccessors(providers, config);
        private final OrgVersionAccessors vaccForOrgVersionAccessors = new OrgVersionAccessors(providers, config);
        public VersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.com</b>
         */
        public ComVersionAccessors getCom() {
            return vaccForComVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.io</b>
         */
        public IoVersionAccessors getIo() {
            return vaccForIoVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.org</b>
         */
        public OrgVersionAccessors getOrg() {
            return vaccForOrgVersionAccessors;
        }

    }

    public static class ComVersionAccessors extends VersionFactory  {

        private final ComCorundumstudioVersionAccessors vaccForComCorundumstudioVersionAccessors = new ComCorundumstudioVersionAccessors(providers, config);
        private final ComGoogleVersionAccessors vaccForComGoogleVersionAccessors = new ComGoogleVersionAccessors(providers, config);
        private final ComMailgunVersionAccessors vaccForComMailgunVersionAccessors = new ComMailgunVersionAccessors(providers, config);
        private final ComMailjetVersionAccessors vaccForComMailjetVersionAccessors = new ComMailjetVersionAccessors(providers, config);
        private final ComStripeVersionAccessors vaccForComStripeVersionAccessors = new ComStripeVersionAccessors(providers, config);
        private final ComVladmihalceaVersionAccessors vaccForComVladmihalceaVersionAccessors = new ComVladmihalceaVersionAccessors(providers, config);
        public ComVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.com.corundumstudio</b>
         */
        public ComCorundumstudioVersionAccessors getCorundumstudio() {
            return vaccForComCorundumstudioVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.com.google</b>
         */
        public ComGoogleVersionAccessors getGoogle() {
            return vaccForComGoogleVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.com.mailgun</b>
         */
        public ComMailgunVersionAccessors getMailgun() {
            return vaccForComMailgunVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.com.mailjet</b>
         */
        public ComMailjetVersionAccessors getMailjet() {
            return vaccForComMailjetVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.com.stripe</b>
         */
        public ComStripeVersionAccessors getStripe() {
            return vaccForComStripeVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.com.vladmihalcea</b>
         */
        public ComVladmihalceaVersionAccessors getVladmihalcea() {
            return vaccForComVladmihalceaVersionAccessors;
        }

    }

    public static class ComCorundumstudioVersionAccessors extends VersionFactory  {

        private final ComCorundumstudioSocketioVersionAccessors vaccForComCorundumstudioSocketioVersionAccessors = new ComCorundumstudioSocketioVersionAccessors(providers, config);
        public ComCorundumstudioVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.com.corundumstudio.socketio</b>
         */
        public ComCorundumstudioSocketioVersionAccessors getSocketio() {
            return vaccForComCorundumstudioSocketioVersionAccessors;
        }

    }

    public static class ComCorundumstudioSocketioVersionAccessors extends VersionFactory  {

        private final ComCorundumstudioSocketioNettyVersionAccessors vaccForComCorundumstudioSocketioNettyVersionAccessors = new ComCorundumstudioSocketioNettyVersionAccessors(providers, config);
        public ComCorundumstudioSocketioVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.com.corundumstudio.socketio.netty</b>
         */
        public ComCorundumstudioSocketioNettyVersionAccessors getNetty() {
            return vaccForComCorundumstudioSocketioNettyVersionAccessors;
        }

    }

    public static class ComCorundumstudioSocketioNettyVersionAccessors extends VersionFactory  {

        public ComCorundumstudioSocketioNettyVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>com.corundumstudio.socketio.netty.socketio</b> with value <b>1.7.19</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getSocketio() { return getVersion("com.corundumstudio.socketio.netty.socketio"); }

    }

    public static class ComGoogleVersionAccessors extends VersionFactory  {

        private final ComGoogleCodeVersionAccessors vaccForComGoogleCodeVersionAccessors = new ComGoogleCodeVersionAccessors(providers, config);
        private final ComGoogleFirebaseVersionAccessors vaccForComGoogleFirebaseVersionAccessors = new ComGoogleFirebaseVersionAccessors(providers, config);
        public ComGoogleVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.com.google.code</b>
         */
        public ComGoogleCodeVersionAccessors getCode() {
            return vaccForComGoogleCodeVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.com.google.firebase</b>
         */
        public ComGoogleFirebaseVersionAccessors getFirebase() {
            return vaccForComGoogleFirebaseVersionAccessors;
        }

    }

    public static class ComGoogleCodeVersionAccessors extends VersionFactory  {

        private final ComGoogleCodeGsonVersionAccessors vaccForComGoogleCodeGsonVersionAccessors = new ComGoogleCodeGsonVersionAccessors(providers, config);
        public ComGoogleCodeVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.com.google.code.gson</b>
         */
        public ComGoogleCodeGsonVersionAccessors getGson() {
            return vaccForComGoogleCodeGsonVersionAccessors;
        }

    }

    public static class ComGoogleCodeGsonVersionAccessors extends VersionFactory  {

        public ComGoogleCodeGsonVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>com.google.code.gson.gson</b> with value <b>2.10.1</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getGson() { return getVersion("com.google.code.gson.gson"); }

    }

    public static class ComGoogleFirebaseVersionAccessors extends VersionFactory  {

        private final ComGoogleFirebaseFirebaseVersionAccessors vaccForComGoogleFirebaseFirebaseVersionAccessors = new ComGoogleFirebaseFirebaseVersionAccessors(providers, config);
        public ComGoogleFirebaseVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.com.google.firebase.firebase</b>
         */
        public ComGoogleFirebaseFirebaseVersionAccessors getFirebase() {
            return vaccForComGoogleFirebaseFirebaseVersionAccessors;
        }

    }

    public static class ComGoogleFirebaseFirebaseVersionAccessors extends VersionFactory  {

        public ComGoogleFirebaseFirebaseVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>com.google.firebase.firebase.admin</b> with value <b>9.2.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getAdmin() { return getVersion("com.google.firebase.firebase.admin"); }

    }

    public static class ComMailgunVersionAccessors extends VersionFactory  {

        private final ComMailgunMailgunVersionAccessors vaccForComMailgunMailgunVersionAccessors = new ComMailgunMailgunVersionAccessors(providers, config);
        public ComMailgunVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.com.mailgun.mailgun</b>
         */
        public ComMailgunMailgunVersionAccessors getMailgun() {
            return vaccForComMailgunMailgunVersionAccessors;
        }

    }

    public static class ComMailgunMailgunVersionAccessors extends VersionFactory  {

        public ComMailgunMailgunVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>com.mailgun.mailgun.java</b> with value <b>1.1.3</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getJava() { return getVersion("com.mailgun.mailgun.java"); }

    }

    public static class ComMailjetVersionAccessors extends VersionFactory  {

        private final ComMailjetMailjetVersionAccessors vaccForComMailjetMailjetVersionAccessors = new ComMailjetMailjetVersionAccessors(providers, config);
        public ComMailjetVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.com.mailjet.mailjet</b>
         */
        public ComMailjetMailjetVersionAccessors getMailjet() {
            return vaccForComMailjetMailjetVersionAccessors;
        }

    }

    public static class ComMailjetMailjetVersionAccessors extends VersionFactory  {

        public ComMailjetMailjetVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>com.mailjet.mailjet.client</b> with value <b>5.2.5</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getClient() { return getVersion("com.mailjet.mailjet.client"); }

    }

    public static class ComStripeVersionAccessors extends VersionFactory  {

        private final ComStripeStripeVersionAccessors vaccForComStripeStripeVersionAccessors = new ComStripeStripeVersionAccessors(providers, config);
        public ComStripeVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.com.stripe.stripe</b>
         */
        public ComStripeStripeVersionAccessors getStripe() {
            return vaccForComStripeStripeVersionAccessors;
        }

    }

    public static class ComStripeStripeVersionAccessors extends VersionFactory  {

        public ComStripeStripeVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>com.stripe.stripe.java</b> with value <b>26.0.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getJava() { return getVersion("com.stripe.stripe.java"); }

    }

    public static class ComVladmihalceaVersionAccessors extends VersionFactory  {

        private final ComVladmihalceaHibernateVersionAccessors vaccForComVladmihalceaHibernateVersionAccessors = new ComVladmihalceaHibernateVersionAccessors(providers, config);
        public ComVladmihalceaVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.com.vladmihalcea.hibernate</b>
         */
        public ComVladmihalceaHibernateVersionAccessors getHibernate() {
            return vaccForComVladmihalceaHibernateVersionAccessors;
        }

    }

    public static class ComVladmihalceaHibernateVersionAccessors extends VersionFactory  {

        private final ComVladmihalceaHibernateTypesVersionAccessors vaccForComVladmihalceaHibernateTypesVersionAccessors = new ComVladmihalceaHibernateTypesVersionAccessors(providers, config);
        public ComVladmihalceaHibernateVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.com.vladmihalcea.hibernate.types</b>
         */
        public ComVladmihalceaHibernateTypesVersionAccessors getTypes() {
            return vaccForComVladmihalceaHibernateTypesVersionAccessors;
        }

    }

    public static class ComVladmihalceaHibernateTypesVersionAccessors extends VersionFactory  {

        public ComVladmihalceaHibernateTypesVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>com.vladmihalcea.hibernate.types.v52</b> with value <b>2.9.7</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getV52() { return getVersion("com.vladmihalcea.hibernate.types.v52"); }

    }

    public static class IoVersionAccessors extends VersionFactory  {

        private final IoGithubVersionAccessors vaccForIoGithubVersionAccessors = new IoGithubVersionAccessors(providers, config);
        private final IoJsonwebtokenVersionAccessors vaccForIoJsonwebtokenVersionAccessors = new IoJsonwebtokenVersionAccessors(providers, config);
        private final IoMinioVersionAccessors vaccForIoMinioVersionAccessors = new IoMinioVersionAccessors(providers, config);
        public IoVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.io.github</b>
         */
        public IoGithubVersionAccessors getGithub() {
            return vaccForIoGithubVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.io.jsonwebtoken</b>
         */
        public IoJsonwebtokenVersionAccessors getJsonwebtoken() {
            return vaccForIoJsonwebtokenVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.io.minio</b>
         */
        public IoMinioVersionAccessors getMinio() {
            return vaccForIoMinioVersionAccessors;
        }

    }

    public static class IoGithubVersionAccessors extends VersionFactory  {

        private final IoGithubLong23112002VersionAccessors vaccForIoGithubLong23112002VersionAccessors = new IoGithubLong23112002VersionAccessors(providers, config);
        public IoGithubVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.io.github.long23112002</b>
         */
        public IoGithubLong23112002VersionAccessors getLong23112002() {
            return vaccForIoGithubLong23112002VersionAccessors;
        }

    }

    public static class IoGithubLong23112002VersionAccessors extends VersionFactory  {

        private final IoGithubLong23112002CommonVersionAccessors vaccForIoGithubLong23112002CommonVersionAccessors = new IoGithubLong23112002CommonVersionAccessors(providers, config);
        public IoGithubLong23112002VersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.io.github.long23112002.common</b>
         */
        public IoGithubLong23112002CommonVersionAccessors getCommon() {
            return vaccForIoGithubLong23112002CommonVersionAccessors;
        }

    }

    public static class IoGithubLong23112002CommonVersionAccessors extends VersionFactory  {

        public IoGithubLong23112002CommonVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>io.github.long23112002.common.service</b> with value <b>1.4.1</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getService() { return getVersion("io.github.long23112002.common.service"); }

    }

    public static class IoJsonwebtokenVersionAccessors extends VersionFactory  {

        private final IoJsonwebtokenJjwtVersionAccessors vaccForIoJsonwebtokenJjwtVersionAccessors = new IoJsonwebtokenJjwtVersionAccessors(providers, config);
        public IoJsonwebtokenVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.io.jsonwebtoken.jjwt</b>
         */
        public IoJsonwebtokenJjwtVersionAccessors getJjwt() {
            return vaccForIoJsonwebtokenJjwtVersionAccessors;
        }

    }

    public static class IoJsonwebtokenJjwtVersionAccessors extends VersionFactory  {

        public IoJsonwebtokenJjwtVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>io.jsonwebtoken.jjwt.api</b> with value <b>0.11.5</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getApi() { return getVersion("io.jsonwebtoken.jjwt.api"); }

        /**
         * Version alias <b>io.jsonwebtoken.jjwt.impl</b> with value <b>0.11.5</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getImpl() { return getVersion("io.jsonwebtoken.jjwt.impl"); }

        /**
         * Version alias <b>io.jsonwebtoken.jjwt.jackson</b> with value <b>0.11.5</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getJackson() { return getVersion("io.jsonwebtoken.jjwt.jackson"); }

    }

    public static class IoMinioVersionAccessors extends VersionFactory  {

        public IoMinioVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>io.minio.minio</b> with value <b>8.0.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getMinio() { return getVersion("io.minio.minio"); }

    }

    public static class OrgVersionAccessors extends VersionFactory  {

        private final OrgModelmapperVersionAccessors vaccForOrgModelmapperVersionAccessors = new OrgModelmapperVersionAccessors(providers, config);
        private final OrgPostgresqlVersionAccessors vaccForOrgPostgresqlVersionAccessors = new OrgPostgresqlVersionAccessors(providers, config);
        private final OrgProjectlombokVersionAccessors vaccForOrgProjectlombokVersionAccessors = new OrgProjectlombokVersionAccessors(providers, config);
        private final OrgSpringframeworkVersionAccessors vaccForOrgSpringframeworkVersionAccessors = new OrgSpringframeworkVersionAccessors(providers, config);
        public OrgVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.org.modelmapper</b>
         */
        public OrgModelmapperVersionAccessors getModelmapper() {
            return vaccForOrgModelmapperVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.org.postgresql</b>
         */
        public OrgPostgresqlVersionAccessors getPostgresql() {
            return vaccForOrgPostgresqlVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.org.projectlombok</b>
         */
        public OrgProjectlombokVersionAccessors getProjectlombok() {
            return vaccForOrgProjectlombokVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.org.springframework</b>
         */
        public OrgSpringframeworkVersionAccessors getSpringframework() {
            return vaccForOrgSpringframeworkVersionAccessors;
        }

    }

    public static class OrgModelmapperVersionAccessors extends VersionFactory  {

        public OrgModelmapperVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>org.modelmapper.modelmapper</b> with value <b>2.4.5</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getModelmapper() { return getVersion("org.modelmapper.modelmapper"); }

    }

    public static class OrgPostgresqlVersionAccessors extends VersionFactory  {

        public OrgPostgresqlVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>org.postgresql.postgresql</b> with value <b>42.2.23</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getPostgresql() { return getVersion("org.postgresql.postgresql"); }

    }

    public static class OrgProjectlombokVersionAccessors extends VersionFactory  {

        public OrgProjectlombokVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>org.projectlombok.lombok</b> with value <b>1.18.20</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getLombok() { return getVersion("org.projectlombok.lombok"); }

    }

    public static class OrgSpringframeworkVersionAccessors extends VersionFactory  {

        private final OrgSpringframeworkBootVersionAccessors vaccForOrgSpringframeworkBootVersionAccessors = new OrgSpringframeworkBootVersionAccessors(providers, config);
        private final OrgSpringframeworkCloudVersionAccessors vaccForOrgSpringframeworkCloudVersionAccessors = new OrgSpringframeworkCloudVersionAccessors(providers, config);
        private final OrgSpringframeworkRetryVersionAccessors vaccForOrgSpringframeworkRetryVersionAccessors = new OrgSpringframeworkRetryVersionAccessors(providers, config);
        public OrgSpringframeworkVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.org.springframework.boot</b>
         */
        public OrgSpringframeworkBootVersionAccessors getBoot() {
            return vaccForOrgSpringframeworkBootVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.org.springframework.cloud</b>
         */
        public OrgSpringframeworkCloudVersionAccessors getCloud() {
            return vaccForOrgSpringframeworkCloudVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.org.springframework.retry</b>
         */
        public OrgSpringframeworkRetryVersionAccessors getRetry() {
            return vaccForOrgSpringframeworkRetryVersionAccessors;
        }

    }

    public static class OrgSpringframeworkBootVersionAccessors extends VersionFactory  {

        private final OrgSpringframeworkBootSpringVersionAccessors vaccForOrgSpringframeworkBootSpringVersionAccessors = new OrgSpringframeworkBootSpringVersionAccessors(providers, config);
        public OrgSpringframeworkBootVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.org.springframework.boot.spring</b>
         */
        public OrgSpringframeworkBootSpringVersionAccessors getSpring() {
            return vaccForOrgSpringframeworkBootSpringVersionAccessors;
        }

    }

    public static class OrgSpringframeworkBootSpringVersionAccessors extends VersionFactory  {

        private final OrgSpringframeworkBootSpringBootVersionAccessors vaccForOrgSpringframeworkBootSpringBootVersionAccessors = new OrgSpringframeworkBootSpringBootVersionAccessors(providers, config);
        public OrgSpringframeworkBootSpringVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.org.springframework.boot.spring.boot</b>
         */
        public OrgSpringframeworkBootSpringBootVersionAccessors getBoot() {
            return vaccForOrgSpringframeworkBootSpringBootVersionAccessors;
        }

    }

    public static class OrgSpringframeworkBootSpringBootVersionAccessors extends VersionFactory  {

        private final OrgSpringframeworkBootSpringBootStarterVersionAccessors vaccForOrgSpringframeworkBootSpringBootStarterVersionAccessors = new OrgSpringframeworkBootSpringBootStarterVersionAccessors(providers, config);
        public OrgSpringframeworkBootSpringBootVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.org.springframework.boot.spring.boot.starter</b>
         */
        public OrgSpringframeworkBootSpringBootStarterVersionAccessors getStarter() {
            return vaccForOrgSpringframeworkBootSpringBootStarterVersionAccessors;
        }

    }

    public static class OrgSpringframeworkBootSpringBootStarterVersionAccessors extends VersionFactory  {

        private final OrgSpringframeworkBootSpringBootStarterDataVersionAccessors vaccForOrgSpringframeworkBootSpringBootStarterDataVersionAccessors = new OrgSpringframeworkBootSpringBootStarterDataVersionAccessors(providers, config);
        public OrgSpringframeworkBootSpringBootStarterVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>org.springframework.boot.spring.boot.starter.test</b> with value <b>2.5.4</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getTest() { return getVersion("org.springframework.boot.spring.boot.starter.test"); }

        /**
         * Version alias <b>org.springframework.boot.spring.boot.starter.validation</b> with value <b>2.5.4</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getValidation() { return getVersion("org.springframework.boot.spring.boot.starter.validation"); }

        /**
         * Version alias <b>org.springframework.boot.spring.boot.starter.web</b> with value <b>2.5.4</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getWeb() { return getVersion("org.springframework.boot.spring.boot.starter.web"); }

        /**
         * Group of versions at <b>versions.org.springframework.boot.spring.boot.starter.data</b>
         */
        public OrgSpringframeworkBootSpringBootStarterDataVersionAccessors getData() {
            return vaccForOrgSpringframeworkBootSpringBootStarterDataVersionAccessors;
        }

    }

    public static class OrgSpringframeworkBootSpringBootStarterDataVersionAccessors extends VersionFactory  {

        public OrgSpringframeworkBootSpringBootStarterDataVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>org.springframework.boot.spring.boot.starter.data.jpa</b> with value <b>2.5.4</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getJpa() { return getVersion("org.springframework.boot.spring.boot.starter.data.jpa"); }

        /**
         * Version alias <b>org.springframework.boot.spring.boot.starter.data.mongodb</b> with value <b>2.5.4</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getMongodb() { return getVersion("org.springframework.boot.spring.boot.starter.data.mongodb"); }

        /**
         * Version alias <b>org.springframework.boot.spring.boot.starter.data.redis</b> with value <b>2.5.4</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getRedis() { return getVersion("org.springframework.boot.spring.boot.starter.data.redis"); }

    }

    public static class OrgSpringframeworkCloudVersionAccessors extends VersionFactory  {

        private final OrgSpringframeworkCloudSpringVersionAccessors vaccForOrgSpringframeworkCloudSpringVersionAccessors = new OrgSpringframeworkCloudSpringVersionAccessors(providers, config);
        public OrgSpringframeworkCloudVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.org.springframework.cloud.spring</b>
         */
        public OrgSpringframeworkCloudSpringVersionAccessors getSpring() {
            return vaccForOrgSpringframeworkCloudSpringVersionAccessors;
        }

    }

    public static class OrgSpringframeworkCloudSpringVersionAccessors extends VersionFactory  {

        private final OrgSpringframeworkCloudSpringCloudVersionAccessors vaccForOrgSpringframeworkCloudSpringCloudVersionAccessors = new OrgSpringframeworkCloudSpringCloudVersionAccessors(providers, config);
        public OrgSpringframeworkCloudSpringVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.org.springframework.cloud.spring.cloud</b>
         */
        public OrgSpringframeworkCloudSpringCloudVersionAccessors getCloud() {
            return vaccForOrgSpringframeworkCloudSpringCloudVersionAccessors;
        }

    }

    public static class OrgSpringframeworkCloudSpringCloudVersionAccessors extends VersionFactory  {

        private final OrgSpringframeworkCloudSpringCloudStarterVersionAccessors vaccForOrgSpringframeworkCloudSpringCloudStarterVersionAccessors = new OrgSpringframeworkCloudSpringCloudStarterVersionAccessors(providers, config);
        public OrgSpringframeworkCloudSpringCloudVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.org.springframework.cloud.spring.cloud.starter</b>
         */
        public OrgSpringframeworkCloudSpringCloudStarterVersionAccessors getStarter() {
            return vaccForOrgSpringframeworkCloudSpringCloudStarterVersionAccessors;
        }

    }

    public static class OrgSpringframeworkCloudSpringCloudStarterVersionAccessors extends VersionFactory  {

        public OrgSpringframeworkCloudSpringCloudStarterVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>org.springframework.cloud.spring.cloud.starter.openfeign</b> with value <b>3.0.4</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getOpenfeign() { return getVersion("org.springframework.cloud.spring.cloud.starter.openfeign"); }

    }

    public static class OrgSpringframeworkRetryVersionAccessors extends VersionFactory  {

        private final OrgSpringframeworkRetrySpringVersionAccessors vaccForOrgSpringframeworkRetrySpringVersionAccessors = new OrgSpringframeworkRetrySpringVersionAccessors(providers, config);
        public OrgSpringframeworkRetryVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.org.springframework.retry.spring</b>
         */
        public OrgSpringframeworkRetrySpringVersionAccessors getSpring() {
            return vaccForOrgSpringframeworkRetrySpringVersionAccessors;
        }

    }

    public static class OrgSpringframeworkRetrySpringVersionAccessors extends VersionFactory  {

        public OrgSpringframeworkRetrySpringVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>org.springframework.retry.spring.retry</b> with value <b>1.3.1</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getRetry() { return getVersion("org.springframework.retry.spring.retry"); }

    }

    public static class BundleAccessors extends BundleFactory {

        public BundleAccessors(ObjectFactory objects, ProviderFactory providers, DefaultVersionCatalog config, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) { super(objects, providers, config, attributesFactory, capabilityNotationParser); }

    }

    public static class PluginAccessors extends PluginFactory {

        public PluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

    }

}
